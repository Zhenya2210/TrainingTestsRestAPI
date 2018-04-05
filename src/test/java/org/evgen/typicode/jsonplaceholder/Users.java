package org.evgen.typicode.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.evgen.HelperTest;
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

    public static final String REGEX = "^((\\w|[-+])+(\\.[\\w-]+)*@[\\w-]+((\\.[\\d\\p{Alpha}]+)*(\\.\\p{Alpha}{2,})*)*)$";

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/users/";
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void getUsersStatusCodeSuccessful(int parameterRequest) {
        given().
                when().
                    get(Integer.toString(parameterRequest)).
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
                    get(parameterRequest).
                then().
                    assertThat().
                    contentType(ContentType.JSON).
                    statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @MethodSource("valuesForMethodGetUsers")
    public void getUsersEmail(String parameterRequest) {

        JsonPath jsonPath = HelperTest.getJsonPath(parameterRequest);

        String email = jsonPath.getString("email");

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(email);
        String validity = "";
        while (matcher.find()) {
            validity = email.substring(matcher.start(), matcher.end());
        }

        assertSame(email, validity, "E-mail is not correct"); //check e-mail
    }

    @ParameterizedTest
    @MethodSource("valuesForMethodGetUsers")
    public void getUsersID(String parameterRequest) {

        JsonPath jsonPath = HelperTest.getJsonPath(parameterRequest);

        String id = jsonPath.getString("id");

        assertEquals(parameterRequest, id, "id doesn't match the query"); //check id

    }

    @ParameterizedTest
    @MethodSource("valuesForMethodGetUsers")
    public void getUsersLatitude(String parameterRequest) {

        JsonPath jsonPath = HelperTest.getJsonPath(parameterRequest);

        double lat = jsonPath.getDouble("address.geo.lat");

        assertTrue(lat <= 90.0 && lat >= -90.0, "Latitude in unacceptable limits.");
    }

    @ParameterizedTest
    @MethodSource("valuesForMethodGetUsers")
    public void existZipcode(String parameterRequest) {
        given().
                when().
                    get(parameterRequest).
                then().
                    body("address", hasKey("zipcode"));
    }

    @ParameterizedTest
    @MethodSource("valuesForMethodGetUsers")
    public void getUsersLongitude(String parameterRequest) {

        JsonPath jsonPath = HelperTest.getJsonPath(parameterRequest);

        double lng = jsonPath.getDouble("address.geo.lng");

        assertTrue(lng <= 180.0 && lng >= -180.0, "Longitude in unacceptable limits.");

    }

    public static Stream<String> valuesForMethodGetUsers() {
        return HelperTest.getAllUserIDs();
    }
}
