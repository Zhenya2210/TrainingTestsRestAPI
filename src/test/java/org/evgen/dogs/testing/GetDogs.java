package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetDogs {

    private static List<String> idDogs = new ArrayList<>();

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "http://localhost:8085/dog/";

        List<Dog> dogs = HelperTest.getCorrectDogs();

        for (int i = 0; i < dogs.size(); i++){

            String idDog = given().
                        contentType("application/json").
                        body(dogs.get(i)).
                    when().
                        post().
                    then().
                        extract().path("id");

            idDogs.add(idDog);
        }
    }


    @ParameterizedTest
    @MethodSource("getIdDogs")
    public void getCorrectDogs(String idDog){

        String actualIdDog = given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
            when().
                get(idDog).
            then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
            extract().
                path("id");

        assertEquals(idDog, actualIdDog);
    }


    public static List<String> getIdDogs(){
        return idDogs;
    }
}
