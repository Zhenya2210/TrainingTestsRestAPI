package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class GetDogs {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "http://localhost:8085/dog/";

    }

    @ParameterizedTest
    @MethodSource("getIdCorrectDogs")
    public void getCorrectDogs(String idDog){

        given().
                accept(ContentType.JSON).
            when().
                get(idDog).
            then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                contentType(ContentType.JSON).
                body("id", equalTo(idDog)).
                body("", hasKey("name")).
                body("", hasKey("timeOfBirth")).
                body("", hasKey("weight")).
                body("", hasKey("height"));

    }

    private static List<String> getIdCorrectDogs(){
        return HelperTest.getIdCorrectDogs();
    }

}
