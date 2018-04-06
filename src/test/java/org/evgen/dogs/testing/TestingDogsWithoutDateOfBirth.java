package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class TestingDogsWithoutDateOfBirth {

    final static String nameOneHundredCharacters = "Представьте себе офисную АТС, которую можно подключить через Интернет в течение нескольких минут, бе";



    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8080/dog/";

    }

    /**
     *
     * Positive test
     */
    @ParameterizedTest
    @MethodSource("valuesForMethodCreateDogs")
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

    public static List<Dog> valuesForMethodCreateDogs() {
        List<Dog> dogs = new ArrayList<>();
            Dog dog1 = new Dog("Kia", 13.0, 50.0);
            Dog dog2 = new Dog("Koko", 50.3, 10.7);
            Dog dog3 = new Dog("Loko", 13.121, 50.34234);
            Dog dog4 = new Dog(nameOneHundredCharacters, 13.121, 50.34234);
        dogs.add(dog1);
        dogs.add(dog2);
        dogs.add(dog3);
        dogs.add(dog4);

        return dogs;
    }
}
