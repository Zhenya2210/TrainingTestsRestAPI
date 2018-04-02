package jsonplaceholder.typicode.com;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.Matchers.equalTo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.*;

public class Users {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void getUsersStatusCodeSuccessful(int parameterRequest) {
        given().
            when().
                get("/users/" + parameterRequest).
            then().
                assertThat().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_OK).
                body("id", equalTo(parameterRequest));

    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "0"})
    public void getUsersStatusCodeNotFound(String parameterRequest) {
        given().
                when().
                    get("/users/" + parameterRequest).
                then().
                    assertThat().
                    contentType(ContentType.JSON).
                    statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void getUsersEmail(String parameterRequest){

        JsonPath jsonPath = EVGHelper.jsonPathUsers(parameterRequest);

        String email = jsonPath.getString("email");

        Pattern pattern = Pattern.compile("^((\\w|[-+])+(\\.[\\w-]+)*@[\\w-]+((\\.[\\d\\p{Alpha}]+)*(\\.\\p{Alpha}{2,})*)*)$");
        Matcher matcher = pattern.matcher(email);
        String validity = "";
        while (matcher.find()){
            validity = email.substring(matcher.start(), matcher.end());
        }

        assertSame(email, validity, "E-mail is not correct"); //check e-mail
    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void getUsersID(String parameterRequest){

        JsonPath jsonPath = EVGHelper.jsonPathUsers(parameterRequest);

        int id = jsonPath.getInt("id");

        assertEquals(Integer.parseInt(parameterRequest), id, "id не совпадает с запросом"); //check id

    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void getUsersLat(String parameterRequest){

        JsonPath jsonPath = EVGHelper.jsonPathUsers(parameterRequest);

        double lat = jsonPath.getDouble("address.geo.lat");

        assertTrue(lat <= 90.0 && lat >= -90.0, "Широта в недопустимых пределах");
    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void existZipcode(String parameterRequest){
        given().
                when().
                    get("/users/" + parameterRequest).
                then().
                    body("address", hasKey("zipcode"));
    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void getUsersLng(String parameterRequest){

        JsonPath jsonPath = EVGHelper.jsonPathUsers(parameterRequest);

        double lng = jsonPath.getDouble("address.geo.lng");

        assertTrue(lng <= 180.0 && lng >= -180.0, "Долгота в недопустимых пределах");

    }

    public static Stream<String> valueForMethodSource(){
        return EVGHelper.userIDs();
    }
}
