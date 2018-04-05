package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.util.Date;

import static io.restassured.RestAssured.given;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class TestingDogs {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8080/dog";

    }

    @Test
    public void createNewDog() throws ParseException {

        Dog dog = new Dog("Dino", 33.0, 70.0);

        given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(200);

       Dog dog2 = new Dog("Shpuntik", 12.3, 15.7, "2010-10-22");

        given().
                contentType("application/json").
                body(dog2).
                when().
                post().
                then().
                assertThat().
                statusCode(200);


    }
}
