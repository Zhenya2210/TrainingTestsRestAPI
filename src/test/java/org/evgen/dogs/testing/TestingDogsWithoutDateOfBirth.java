package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class TestingDogsWithoutDateOfBirth {


    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8080/dog/";

    }

    /**
     *
     * Positive test
     */
    @ParameterizedTest
    @MethodSource("getCorrectDogs")
    public void createNewDog(Dog dog){

        String idDogExpected = given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(200).
                extract().
                    path("id");

        String idDogActual = given().
                                contentType("application/json").
                            when().
                                get(idDogExpected).
                            then().
                                assertThat().
                                statusCode(200).
                            extract().
                                path("id");

        assertEquals(idDogExpected, idDogActual);

        given().
                contentType("application/json").
                when().
                    delete(idDogExpected).
                then().
                    assertThat().
                        statusCode(204);

        given().
                contentType("application/json").
                when().
                    get(idDogExpected).
                then().
                    assertThat().
                    statusCode(404);

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
