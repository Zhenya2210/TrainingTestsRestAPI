package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class TestingPostDogsWithoutDateOfBirth {


    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8085/dog/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

    }

    @ParameterizedTest
    @MethodSource("getCorrectDogs")
    public void crateNewCorrectDog(Dog dog){

        JsonPath jsonPathNewDog = given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(200).
                extract().
                    jsonPath();

        String actualName = jsonPathNewDog.getString("name");
        double actualHeight = jsonPathNewDog.getDouble("height");
        double actualWeight = jsonPathNewDog.getDouble("weight");
        String dateOfBirth = jsonPathNewDog.getString("dateOfBirth");

        assertNull(dateOfBirth, "Date of birth isn't null");
        assertEquals(dog.getName(), actualName, "The name doesn't match the name you added earlier");
        assertEquals(Double.parseDouble(dog.getHeight()), actualHeight, "Height doesn't match the height you added earlier");
        assertEquals(Double.parseDouble(dog.getWeight()), actualWeight, "Weight doesn't match the weight you added earlier");

    }


    @ParameterizedTest
    @MethodSource("getDogsWithIncorrectName")
    public void errorCodeNotBlankSized(Dog dog){

        JsonPath jsonPathDog = given().
                contentType("application/json").
                body(dog).
            when().
                post().
            then().
                assertThat().
                statusCode(400).
            extract().
                jsonPath();

        String fieldError = jsonPathDog.getString("field");
        String errorCode = jsonPathDog.getString("errorCode");
        String errorMessage = jsonPathDog.getString("errorMessage");
        assertEquals("[name]", fieldError);
        assertEquals("[NotBlankSized]", errorCode);
        assertEquals("[размер должен быть между 1 и 100]", errorMessage);

    }

    @ParameterizedTest
    @MethodSource("getDogsWithIncorrectWeight")
    public void dogsWithIncorrectWeight(Dog dog){

        JsonPath jsonPath = given().
                                contentType("application/json").
                                body(dog).
                            when().
                                post().
                            then().
                                assertThat().
                                statusCode(400).
                            extract().
                                jsonPath();

        String fieldError = jsonPath.getString("field");
        String errorCode = jsonPath.getString("errorCode");
        String errorMessage = jsonPath.getString("errorMessage");
        assertEquals("[weight]", fieldError);
        assertEquals("[DecimalMin]", errorCode);
        assertEquals("[должно быть больше чем 0]", errorMessage);
    }

    @ParameterizedTest
    @MethodSource("getDogsWithIncorrectHeight")
    public void dogsWithIncorrectHeight(Dog dog){

        JsonPath jsonPath = given().
                contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(400).
                extract().
                    jsonPath();

        String fieldError = jsonPath.getString("field");
        String errorCode = jsonPath.getString("errorCode");
        String errorMessage = jsonPath.getString("errorMessage");
        assertEquals("[height]", fieldError);
        assertEquals("[DecimalMin]", errorCode);
        assertEquals("[должно быть больше чем 0]", errorMessage);
    }

    @ParameterizedTest
    @MethodSource("getDogsWithWrongOtherValues")
    public void createDogsWithWrongOtherValues(Dog dog){
        given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(400);
    }

    public static List<Dog> getDogsWithWrongOtherValues(){
        return HelperTest.getDogsWithWrongOtherValues();
    }


    public static List<Dog> getCorrectDogs() {

        return HelperTest.getCorrectDogs();
    }

    public static List<Dog> getDogsWithIncorrectWeight() {

        return HelperTest.getDogsWithIncorrectWeight();
    }

    public static List<Dog> getDogsWithIncorrectHeight() {

        return HelperTest.getDogsWithIncorrectHeight();
    }

    public static List<Dog> getDogsWithIncorrectName(){

       return HelperTest.getDogsWithIncorrectName();
    }
}
