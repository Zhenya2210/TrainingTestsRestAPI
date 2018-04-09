package org.evgen.dogs.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.apache.http.HttpStatus;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing application github: https://github.com/timazet/java-course
 */
public class PostDogs {


    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://localhost:8085/dog/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

    }

    @ParameterizedTest
    @MethodSource("getCorrectDogs")
    public void crateNewCorrectDog(Dog dog){

        JsonPath jsonPathNewDog = given().
                    contentType(ContentType.JSON).
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    jsonPath();

        String actualName = jsonPathNewDog.getString("name");
        double actualHeight = jsonPathNewDog.getDouble("height");
        double actualWeight = jsonPathNewDog.getDouble("weight");
        String timeOfBirth = jsonPathNewDog.getString("timeOfBirth");

        assertNull(timeOfBirth, "Date of birth isn't null");
        assertEquals(dog.getName(), actualName, "The name doesn't match the name you added earlier");
        assertEquals(dog.getHeight(), actualHeight, "Height doesn't match the height you added earlier");
        assertEquals(dog.getWeight(), actualWeight, "Weight doesn't match the weight you added earlier");

    }

    @ParameterizedTest
    @MethodSource("getCorrectDogsSpecialCases")
    public void crateNewCorrectDogSpecialCase(DogSpecialCase dog){

        JsonPath jsonPathNewDog = given().
                    contentType(ContentType.JSON).
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    jsonPath();

        String actualName = jsonPathNewDog.getString("name");
        double actualHeight = jsonPathNewDog.getDouble("height");
        double actualWeight = jsonPathNewDog.getDouble("weight");
        String timeOfBirth = jsonPathNewDog.getString("timeOfBirth");

        assertNull(timeOfBirth, "Date of birth isn't null");
        assertEquals(dog.getName(), actualName, "The name doesn't match the name you added earlier");
        assertEquals(Double.parseDouble(dog.getHeight()), actualHeight, "Height doesn't match the height you added earlier");
        assertEquals(Double.parseDouble(dog.getWeight()), actualWeight, "Weight doesn't match the weight you added earlier");

    }


    @ParameterizedTest
    @MethodSource("getDogsWithIncorrectName")
    public void errorCodeNotBlankSized(Dog dog){

        JsonPath jsonPathDog = given().
                contentType(ContentType.JSON).
                body(dog).
            when().
                post().
            then().
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
            extract().
                jsonPath();

        String fieldError = jsonPathDog.getString("field");
        String errorCode = jsonPathDog.getString("errorCode");
        String errorMessage = jsonPathDog.getString("errorMessage");
        assertEquals("[name]", fieldError);
        assertEquals("[NotBlankSized]", errorCode);
        assertEquals("[размер должен быть между 1 и 100]", errorMessage);

    }

    @ParameterizedTest
    @MethodSource("getDogsWithIncorrectWeight")
    public void dogsWithIncorrectWeight(Dog dog){

        JsonPath jsonPath = given().
                                contentType(ContentType.JSON).
                                body(dog).
                            when().
                                post().
                            then().
                                assertThat().
                                statusCode(HttpStatus.SC_BAD_REQUEST).
                            extract().
                                jsonPath();

        String fieldError = jsonPath.getString("field");
        String errorCode = jsonPath.getString("errorCode");
        String errorMessage = jsonPath.getString("errorMessage");
        assertEquals("[weight]", fieldError);
        assertEquals("[DecimalMin]", errorCode);
        assertEquals("[должно быть больше чем 0]", errorMessage);
    }

    @ParameterizedTest
    @MethodSource("getDogsWithIncorrectHeight")
    public void dogsWithIncorrectHeight(Dog dog){

        JsonPath jsonPath = given().
                contentType(ContentType.JSON).
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(HttpStatus.SC_BAD_REQUEST).
                extract().
                    jsonPath();

        String fieldError = jsonPath.getString("field");
        String errorCode = jsonPath.getString("errorCode");
        String errorMessage = jsonPath.getString("errorMessage");
        assertEquals("[height]", fieldError);
        assertEquals("[DecimalMin]", errorCode);
        assertEquals("[должно быть больше чем 0]", errorMessage);
    }

    @ParameterizedTest
    @MethodSource("getDogsWithWrongOtherValues")
    public void createDogsWithWrongOtherValues(DogSpecialCase dog){
        given().
                    contentType(ContentType.JSON).
                    body(dog).
                when().
                    post().
                then().
                    assertThat().
                    statusCode(HttpStatus.SC_BAD_REQUEST);
    }

//    @ParameterizedTest
//    @MethodSource("getCorrectDogsWithDateOfBirth")
//    public void createCorrectDogsWithDateOfBirth(Dog dog) throws ParseException {
//
//        JsonPath jsonPathNewDog = given().
//                    contentType("application/json").
//                    body(dog).
//                when().
//                    post().
//                then().
//                    assertThat().
//                    statusCode(200).
//                extract().
//                    jsonPath();
//
//        String actualName = jsonPathNewDog.getString("name");
//        double actualHeight = jsonPathNewDog.getDouble("height");
//        double actualWeight = jsonPathNewDog.getDouble("weight");
//        String actualTimeOfBirth = jsonPathNewDog.getString("timeOfBirth");
//
//        String expectedTimeOfBirth = dog.getTimeOfBirth();
//
//        String utc = expectedTimeOfBirth.substring(expectedTimeOfBirth.length() - 5, expectedTimeOfBirth.length()-2) + ":" + expectedTimeOfBirth.substring(expectedTimeOfBirth.length() - 2, expectedTimeOfBirth.length()) ;
//
////        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXXX");
//
//        LocalDateTime localDate = LocalDateTime.parse(expectedTimeOfBirth, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXXX"));
//        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDate, offset);
//        OffsetDateTime offsetDateTime1 =
////        OffsetDateTime offsetDateTime2 = OffsetDateTime.of(offsetDateTime, ZoneOffset.of("+00:00"));
//        System.out.println(offsetDateTime);
//
//
//        assertEquals(dog.getTimeOfBirth(), actualTimeOfBirth);
//        assertEquals(dog.getName(), actualName, "The name doesn't match the name you added earlier");
//        assertEquals(dog.getHeight(), actualHeight, "Height doesn't match the height you added earlier");
//        assertEquals(dog.getWeight(), actualWeight, "Weight doesn't match the weight you added earlier");
//
//    }

    private static List<DogSpecialCase> getDogsWithWrongOtherValues(){
        return HelperTest.getDogsWithWrongOtherValues();
    }


    private static List<Dog> getCorrectDogs() {

        return HelperTest.getCorrectDogs();
    }

    private static List<Dog> getDogsWithIncorrectWeight() {

        return HelperTest.getDogsWithIncorrectWeight();
    }

    private static List<Dog> getDogsWithIncorrectHeight() {

        return HelperTest.getDogsWithIncorrectHeight();
    }

    private static List<Dog> getDogsWithIncorrectName(){

       return HelperTest.getDogsWithIncorrectName();
    }

    private static List<DogSpecialCase> getCorrectDogsSpecialCases(){
       return HelperTest.getCorrectDogsSpecialCases();
    }

    public static List<Dog> getCorrectDogsWithDateOfBirth() throws ParseException {
        return HelperTest.getCorrectDogsWithDateOfBirth();
    }
}
