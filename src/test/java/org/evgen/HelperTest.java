package org.evgen;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.evgen.dogs.testing.Dog;
import org.evgen.dogs.testing.DogSpecialCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        dogs.add(new Dog("I", 13.04, 50.66));
        dogs.add(new Dog("Di", 50, 10));
        dogs.add(new Dog(nameOneHundredCharacters, 13.0, 50.0));
        dogs.add(new Dog(nameOneHundredMinusOneCharacters, 20.1, 14.5));
        dogs.add(new Dog("Scooby doo", 1.7e+308, 13));
        dogs.add(new Dog("Goofy", 8, 1.7e+308));
        dogs.add(new Dog("Pluto", 0.01, 0.01));
        dogs.add(new Dog("Max Goof", 0.095765765875487623854832648732, 50));

        return dogs;
    }

    public static List<Dog> getDogsWithIncorrectWeight(){

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Dino", 0, 8));
        dogs.add(new Dog("Dino", -1, 8.0));

        return dogs;
    }

    public static List<Dog> getDogsWithIncorrectHeight(){

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Dino", 8, 0));
        dogs.add(new Dog("Dino", 8.0, -1));

        return dogs;
    }

    public static List<Dog> getDogsWithIncorrectName(){
        final String nameOneHundredPlusOneCharacters = "Представьте себе офисную ATC, которую можно подключить через Интернет в течение нескольких минут, без";

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("", 13.0, 50.0));
        dogs.add(new Dog(nameOneHundredPlusOneCharacters, 13.0, 50.0));
        dogs.add(new Dog(" ", 13.0, 50.0));

        return dogs;
    }

    public static List<DogSpecialCase> getDogsWithWrongOtherValues(){

        List<DogSpecialCase> dogs = new ArrayList<>();
        dogs.add(new DogSpecialCase("Scrappy doo", "50.0 + 34.8", "50.0"));
        dogs.add(new DogSpecialCase("Droopy", "13.0", "50.0 + 28.3"));
        dogs.add(new DogSpecialCase("Spike", "60 oz.", "50.98"));
        dogs.add(new DogSpecialCase("Hachiko", "13.0", "35 in."));

        return dogs;
    }

    public static List<DogSpecialCase> getCorrectDogsSpecialCases(){

        List<DogSpecialCase> dogs = new ArrayList<>();

        dogs.add(new DogSpecialCase("Deput@t", "1.8e+308", "50"));
        dogs.add(new DogSpecialCase("Rex", "13.04", "1.8e+308"));

        return dogs;
    }

    public static List<Dog> getCorrectDogsWithDateOfBirth() throws ParseException {
        List<Dog> dogs = new ArrayList<>();

        dogs.add(new Dog("Shaggy", 154.5, 178, "0714-06-23T13:00:00.670+0500"));

        return dogs;
    }

    public static List<String> getIdCorrectDogs(){

        List<Dog> dogs = HelperTest.getCorrectDogs();
        List<String> idCorrectDogs = new ArrayList<>();
        for (int i = 0; i < dogs.size(); i++){

            String idDog = given().
                        contentType("application/json").
                        body(dogs.get(i)).
                    when().
                        post().
                    then().
                        extract().path("id");

            idCorrectDogs.add(idDog);
        }
        return idCorrectDogs;
    }

    public static String getTimeOfBirthWithZeroTimeZone(String dogTimeOfBrith){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXXX");
        ZonedDateTime fromIsoDate = ZonedDateTime.parse(dogTimeOfBrith, dateTimeFormatter);
        ZoneOffset offset = ZoneOffset.of("+00:00");
        ZonedDateTime acst = fromIsoDate.withZoneSameInstant(offset);
        dogTimeOfBrith = acst.format(dateTimeFormatter);

        return dogTimeOfBrith;
    }
}
