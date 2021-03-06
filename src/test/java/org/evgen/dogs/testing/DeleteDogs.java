package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class DeleteDogs {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://localhost:8085/dog/";
    }


    @ParameterizedTest
    @MethodSource("getIdCorrectDogs")
    public void deleteDogs(String idDog){

        given().
            when().
                delete(idDog).
            then().
                assertThat().
                statusCode(HttpStatus.SC_NO_CONTENT);

        given().
            when().
                get(idDog).
            then().
                assertThat().
                statusCode(HttpStatus.SC_NOT_FOUND);

    }

    private static List<String> getIdCorrectDogs(){
        return HelperTest.getIdCorrectDogs();
    }
}
