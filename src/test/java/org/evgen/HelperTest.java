package org.evgen;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.evgen.dogs.testing.Dog;
import org.evgen.dogs.testing.DogSpecialCase;

import java.text.ParseException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HelperTest {

    private HelperTest() {
    }

    public static JsonPath getJsonPath(String path, Object... parametersPath) {

        return given().
                    accept(ContentType.JSON).
                when().
                    get(path, parametersPath).
                thenReturn().
                    jsonPath();
    }

    public static List<Dog> getCorrectDogs(){
        final String nameOneHundredCharacters = "Собаки - лучшие друзья человека, ведь благодаря своему чутью, выносливости и особенному характеру он";
        final String nameOneHundredMinusOneCharacters = "Собаки - лучшие друзья человека, ведь благодаря своему чутью, выносливости и особенному характеру о";

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
        final String nameOneHundredPlusOneCharacters = "Собаки - лучшие друзья человека, ведь благодаря своему чутью, выносливости и особенному характеру они";

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

    public static List<Dog> getDogsWithCorrectDateOfBirth() throws ParseException {
        List<Dog> dogs = new ArrayList<>();

        dogs.add(new Dog("Rex1", 154.5, 178, "2014-06-23T12:34:56.789+1800"));
        dogs.add(new Dog("Rex2", 154.5, 178, "2014-06-23T12:34:56.789-1800"));
        dogs.add(new Dog("Rex3", 154.5, 178, "2014-06-23T12:34:56.789+1759"));
        dogs.add(new Dog("Rex4", 154.5, 178, "2014-06-23T12:34:56.789-1759"));
        dogs.add(new Dog("Rex5", 154.5, 178, "2014-06-23T12:34:56.789-1759"));
        dogs.add(new Dog("Rex6", 154.5, 178, "2014-06-23T12:34:56.789+0000"));
        dogs.add(new Dog("Rex7", 154.5, 178, "2014-06-23T12:34:56.789-0000"));
        dogs.add(new Dog("Rex8", 154.5, 178, "2014-06-23T23:59:59.789+0800"));
        dogs.add(new Dog("Rex9", 154.5, 178, "0862-01-01T12:34:56.789-0300"));

        dogs.add(new Dog("Baron", 154.5, 178, "2014-06-23T12:34:56.07+0830"));

        return dogs;
    }

    public static List<Dog> getDogsWithIncorrectDateOfBirth(){

        List<Dog> dogs = new ArrayList<>();

        dogs.add(new Dog("Baron1", 154.5, 178, "2014-06-23T12:34:56.789+1801"));
        dogs.add(new Dog("Baron2", 154.5, 178, "2014-06-23T12:34:56.789-1801"));
        dogs.add(new Dog("Baron3", 154.5, 178, "2014-06-23T25:34:56.789+0830"));
        dogs.add(new Dog("Baron4", 154.5, 178, "2014-06-23T26:34:56.789+0830"));
        dogs.add(new Dog("Baron5", 154.5, 178, "2014-06-23T12:60:56.789+0830"));
        dogs.add(new Dog("Baron6", 154.5, 178, "2014-06-23T12:61:56.789+0830"));
        dogs.add(new Dog("Baron7", 154.5, 178, "2014-06-23T12:34:60.789+0830"));
        dogs.add(new Dog("Baron8", 154.5, 178, "2014-06-23T12:34:61.789+0830"));
        dogs.add(new Dog("Baron9", 154.5, 178, "2014-06-23T12:34:56.7899+0830"));
        dogs.add(new Dog("Baron10", 154.5, 178, "2014-06-23T12:34:56.789+00830"));
        dogs.add(new Dog("Baron11", 154.5, 178, "2014-06-23T12:34:56.789+08300"));
        dogs.add(new Dog("Baron12", 154.5, 178, "2014-06-23T12:34:56.7+0830"));
        dogs.add(new Dog("Baron13", 154.5, 178, "2014-06-32T12:34:56.007+0830"));
        dogs.add(new Dog("Baron14", 154.5, 178, "2014-06-00T12:34:56.007+0830"));
        dogs.add(new Dog("Baron15", 154.5, 178, "2014-13-03T12:34:56.007+0830"));
        dogs.add(new Dog("Baron16", 154.5, 178, "2014-00-22T12:34:56.007+0830"));
        dogs.add(new Dog("Baron17", 154.5, 178, "2014-09-3T12:34:56.007+0830"));
        dogs.add(new Dog("Baron18", 154.5, 178, "14-09-03T12:34:56.007+0830"));
        dogs.add(new Dog("Baron19", 154.5, 178, "2014-9-03T12:34:56.007+0830"));
        dogs.add(new Dog("Baron20", 154.5, 178, "2014-09-03T12:34:56.007+08:30"));

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
