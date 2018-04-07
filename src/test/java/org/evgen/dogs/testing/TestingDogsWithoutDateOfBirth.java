package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class TestingDogsWithoutDateOfBirth {


    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8085/dog/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

    }

//    /**
//     *
//     * Positive test
//     */
//    @ParameterizedTest
//    @MethodSource("getCorrectDogs")
//    public void createNewDog(Dog dog){
//
//        String idDogExpected = given().
//                    contentType("application/json").
//                    body(dog).
//                when().
//                    post().
//                then().
//                    assertThat().
//                    statusCode(200).
//                extract().
//                    path("id");
//
//        String idDogActual = given().
//                                contentType("application/json").
//                            when().
//                                get(idDogExpected).
//                            then().
//                                assertThat().
//                                statusCode(200).
//                            extract().
//                                path("id");
//
//        assertEquals(idDogExpected, idDogActual);
//
//        given().
//                contentType("application/json").
//                when().
//                    delete(idDogExpected).
//                then().
//                    assertThat().
//                        statusCode(204);
//
//        given().
//                contentType("application/json").
//                when().
//                    get(idDogExpected).
//                then().
//                    assertThat().
//                    statusCode(404);
//
//    }

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

        assertEquals(dog.getName(), actualName, "The name doesn't match the name you added earlier");
        assertEquals(Double.parseDouble(dog.getHeight()), actualHeight, "Height doesn't match the height you added earlier");
        assertEquals(Double.parseDouble(dog.getWeight()), actualWeight, "Weight doesn't match the weight you added earlier");

    }

    @ParameterizedTest
    @MethodSource("getIncorrectDogs")
    public void createNewIncorrectDog(Dog dog){

        given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                statusCode(400);

    }

    public static List<Dog> getCorrectDogs() {

        return HelperTest.getCorrectDogs();
    }

    public static List<Dog> getIncorrectDogs() {

        return HelperTest.getIncorrectDogs();
    }
}
