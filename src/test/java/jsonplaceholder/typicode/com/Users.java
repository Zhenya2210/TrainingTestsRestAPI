package jsonplaceholder.typicode.com;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import jsonplaceholder.typicode.com.JsonResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.Matchers.equalTo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Users {

    @BeforeAll
    public void setUP(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void checkGetUsersStatusCodeSuccessful(int parameterRequest) {
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
    @ValueSource(strings = {"11", "0", "-1", "4.5", "5.0"})
    public void checkGetUsersStatusCodeNotFound(String parameterRequest) {
        given().
                when().
                    get("/users/" + parameterRequest).
                then().
                    assertThat().
                    contentType(ContentType.JSON).
                    statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void checkGetUsersEmail(String parameterRequest){

        JsonPath jsonPath = JsonResponse.jsonPathUsers(parameterRequest);

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
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void checkGetUsersID(String parameterRequest){

        JsonPath jsonPath = JsonResponse.jsonPathUsers(parameterRequest);

        int id = jsonPath.getInt("id");

        assertEquals(Integer.parseInt(parameterRequest), id, "id не совпадает с запросом"); //check id

    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void checkGetUsersLat(String parameterRequest){

        JsonPath jsonPath = JsonResponse.jsonPathUsers(parameterRequest);

        double lat = jsonPath.getDouble("address.geo.lat");

        assertTrue(lat <= 90.0 && lat >= -90.0, "Широта в недопустимых пределах");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void checkGetUsersLng(String parameterRequest){

        JsonPath jsonPath = JsonResponse.jsonPathUsers(parameterRequest);

        double lng = jsonPath.getDouble("address.geo.lng");

        assertTrue(lng <= 180.0 && lng >= -180.0, "Долгота в недопустимых пределах");

    }
}
