package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class TestingDogs {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8080/dog/";

    }

    @Test
    public void createNewDog() throws ParseException {

        Dog dog = new Dog("Dino", 33.0, 70.0);

        String idDogExpected = given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    extract().
                    path("id");

        String idDogActual = given().
                                contentType("application/json").
                            when().
                                get(idDogExpected).
                            then().
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
}
