package services.groupkt.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchCountry {

    private String baseURI = "http://services.groupkt.com/country/search?text={text}";
    private String allCountries = "http://services.groupkt.com/country/get/all";

    @ParameterizedTest
    @ValueSource(strings = {"un", "UN", "zlqx", ""})
    public void searchStatusSC_OK(String searchParameter){

        given().
                    pathParam("text", searchParameter).
                when().
                    get(baseURI).
                then().
                    assertThat().
                    statusCode(HttpStatus.SC_OK).
                    contentType(ContentType.JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void searchCountry(String searchParameter){

        JsonPath jsonPath = EVGHelper.jsonPathSearch(baseURI, searchParameter);
        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        for (int i = 0; i < quantityOfObjects; i++){
            assertTrue((jsonPath.getString("RestResponse.result[" + i + "].name")).toLowerCase().contains(searchParameter.toLowerCase())
            || (jsonPath.getString("RestResponse.result[" + i + "].alpha2_code")).toLowerCase().contains(searchParameter.toLowerCase())
            || (jsonPath.getString("RestResponse.result[" + i + "].alpha3_code")).toLowerCase().contains(searchParameter.toLowerCase()));
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void valueOfMessage(String searchParameter){

        JsonPath jsonPath = EVGHelper.jsonPathSearch(baseURI, searchParameter);

        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        String message = jsonPath.getString("RestResponse.messages");

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(message);
        int valueOfMessage = 0;

        while (matcher.find()){
            valueOfMessage = Integer.parseInt(message.substring(matcher.start(), matcher.end()));
        }

        assertEquals(quantityOfObjects, valueOfMessage);
    }

    @DisplayName("Checking the search result for duplicates")
    @ParameterizedTest
    @ValueSource(strings = {"un", "ru"})
    public void dublicateOfSearchResult(String searchParameter){

        JsonPath jsonPath = EVGHelper.jsonPathSearch(baseURI, searchParameter);

        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        HashMap<String, Integer> map = new HashMap<>();
        String nameOfCountry;

        for (int i = 0; i < quantityOfObjects; i++){
            nameOfCountry = jsonPath.getString("RestResponse.result[" + i + "].name");
                if (!map.containsKey(nameOfCountry)){
                    map.put(nameOfCountry, 1);
                    }
                else
                    {
                    map.put(nameOfCountry, map.get(nameOfCountry) + 1);
                    }
        }

        for(Map.Entry<String, Integer> pair: map.entrySet()){
            int value = pair.getValue();
            assertEquals(1, value);
        }
    }

    @DisplayName("Loss check")
    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void searchCountryLosses(String searchParameter){

        JsonPath jsonPath = EVGHelper.jsonPathSearch(baseURI, searchParameter);
        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        jsonPath = EVGHelper.jsonPathSearch(allCountries);
        int quantityOfFieldsAllCountries = jsonPath.getInt("RestResponse.result.size()");
        int counter = 0;
        for(int i = 0; i < quantityOfFieldsAllCountries; i++){
                if((jsonPath.getString("RestResponse.result[" + i + "].name")).toLowerCase().contains(searchParameter.toLowerCase())
                        || (jsonPath.getString("RestResponse.result[" + i + "].alpha2_code")).toLowerCase().contains(searchParameter.toLowerCase())
                        || (jsonPath.getString("RestResponse.result[" + i + "].alpha3_code")).toLowerCase().contains(searchParameter.toLowerCase())){
                    counter++;
                }
        }
        assertEquals(quantityOfObjects, counter);
    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void searchIncorrectCountry(String searchParameter){
        JsonPath jsonPath = EVGHelper.jsonPathSearch(baseURI, searchParameter);
        String message = jsonPath.getString("RestResponse.messages");

        assertEquals("[No matching country found for requested code [" + searchParameter +"].]", message);
    }

    public static String[] valueForMethodSource(){
        String[] valueForTest = {"8g8", "kc7"};
        return valueForTest;
    }
}
