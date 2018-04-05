package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class TestingDogs {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8080/dog";
    }

    @Test
    public void createNewDog(){

        Dog dog = new Dog("Dino", 33.0, 70.0);

        given().
                    contentType("application/json").
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(200);

    }
}
