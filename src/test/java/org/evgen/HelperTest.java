package org.evgen;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.evgen.dogs.testing.Dog;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class HelperTest {

    private HelperTest() {
    }

    public static Stream<String> getAllUserIDs() {

        JsonPath jsonPath = HelperTest.getJsonPath("https://jsonplaceholder.typicode.com/users/");

        return jsonPath.getList("id", String.class).stream();

    }

    public static JsonPath getJsonPath(String path, Object... parametersPath) {

        return given().
                    accept(ContentType.JSON).
                when().
                    get(path, parametersPath).
                thenReturn().
                    jsonPath();
    }


    public static List<String> getIdPhotos() {

        JsonPath jsonPath = HelperTest.getJsonPath("https://jsonplaceholder.typicode.com/photos/");

        return jsonPath.getList("id", String.class);
    }

    public static List<String> getAllParametersForTestComments() {

        JsonPath jsonPath = HelperTest.getJsonPath("https://jsonplaceholder.typicode.com/comments/");

        return jsonPath.getList("id", String.class);

    }

    public static BufferedImage getPhoto(String idPhoto) throws IOException {

        JsonPath jsonPath = HelperTest.getJsonPath(idPhoto);

        URL url = new URL(jsonPath.getString("url"));
        return ImageIO.read(url);

    }

    public static int getNumericalValueFromMessage(String message) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(message);
        int valueOfMessage = 0;
        while (matcher.find()) {
            valueOfMessage = Integer.parseInt(message.substring(matcher.start(), matcher.end()));
        }
        return valueOfMessage;
    }

    public static List<Dog> getCorrectDogs(){
        final String nameOneHundredCharacters = "Представьте себе офисную ATC, которую можно подключить через Интернет в течение нескольких минут, бе";
        final String nameOneHundredMinusOneCharacters = "Представьте себе офисную ATC, которую можно подключить через Интернет в течение нескольких минут, б";

        List<Dog> dogs = new ArrayList<>();
            Dog dog1 = new Dog("I", 13.0, 50.0);
            Dog dog2 = new Dog("Dino", 50.3, 10.7);
            Dog dog3 = new Dog("Rex", 13.1212394012948537, 50.34234);
            Dog dog4 = new Dog(nameOneHundredCharacters, 13.121, 50.34234);
            Dog dog5 = new Dog(nameOneHundredMinusOneCharacters, 20.1, 14.5);
            Dog dog6 = new Dog("Bobik", 1.7e+308, 1.7e+308);
            Dog dog7 = new Dog("Bobik", 0.1, 0.1);
            Dog dog8 = new Dog("Bobik", 13, 50);
        dogs.add(dog1);
        dogs.add(dog2);
        dogs.add(dog3);
        dogs.add(dog4);
        dogs.add(dog5);
        dogs.add(dog6);
        dogs.add(dog7);
        dogs.add(dog8);
        return dogs;
    }

    public static List<Dog> getIncorrectDogs(){
        final String nameOneHundredPlusOneCharacters = "Представьте себе офисную ATC, которую можно подключить через Интернет в течение нескольких минут, без";
        
        List<Dog> dogs = new ArrayList<>();

        Dog dog1 = new Dog("", 13.0, 50.0);
        Dog dog2 = new Dog("Dino", 0, 0);
        Dog dog3 = new Dog("Dino", -1, -1);
        Dog dog4 = new Dog(nameOneHundredPlusOneCharacters, 13.0, 50.0);

        dogs.add(dog1);
        dogs.add(dog2);
        dogs.add(dog3);
        dogs.add(dog4);

        return dogs;
    }

}
