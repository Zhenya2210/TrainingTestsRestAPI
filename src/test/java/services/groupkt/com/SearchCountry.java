package services.groupkt.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchCountry {

    String baseURI = "http://services.groupkt.com/country/search?text={text}";
    String allCountries = "http://services.groupkt.com/country/get/all";

//    @BeforeAll
//    public void setUP() {
//        RestAssured.baseURI = "http://services.groupkt.com/country";
//    }

    @ParameterizedTest
    @ValueSource(strings = {"un", "UN", "zlqx", ""})
    public void checkStatusSC_OK(String searchParameter){

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

        JsonPath jsonPath = JsonResponseSearch.jsonPathSearch(baseURI, searchParameter);
        int countOfFields = jsonPath.getInt("RestResponse.result.size()");

        for (int i = 0; i < countOfFields; i++){
            assertTrue((jsonPath.getString("RestResponse.result[" + i + "].name")).toLowerCase().contains(searchParameter.toLowerCase())
            || (jsonPath.getString("RestResponse.result[" + i + "].alpha2_code")).toLowerCase().contains(searchParameter.toLowerCase())
            || (jsonPath.getString("RestResponse.result[" + i + "].alpha3_code")).toLowerCase().contains(searchParameter.toLowerCase()));
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void checkValueOfMessage(String searchParameter){

        JsonPath jsonPath = JsonResponseSearch.jsonPathSearch(baseURI, searchParameter);

        int countOfFields = jsonPath.getInt("RestResponse.result.size()");

        String message = jsonPath.getString("RestResponse.messages");

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(message);
        int valueOfMessage = 0;

        while (matcher.find()){
            valueOfMessage = Integer.parseInt(message.substring(matcher.start(), matcher.end()));
        }

        assertEquals(countOfFields, valueOfMessage);
    }

    @DisplayName("Checking the search result for duplicates")
    @ParameterizedTest
    @ValueSource(strings = {"un", "ru"})
    public void checkDublicateOfSearchResult(String searchParameter){

        JsonPath jsonPath = JsonResponseSearch.jsonPathSearch(baseURI, searchParameter);

        int countOfFields = jsonPath.getInt("RestResponse.result.size()");

        HashMap<String, Integer> map = new HashMap<>();
        String nameOfCountry;

        for (int i = 0; i < countOfFields; i++){
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

    @DisplayName("Check for No Losses")
    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void checkSearchCountry(String searchParameter){

        JsonPath jsonPath = JsonResponseSearch.jsonPathSearch(baseURI, searchParameter);
        int countOfFields = jsonPath.getInt("RestResponse.result.size()");

        jsonPath = JsonResponseSearch.jsonPathSearch(allCountries);
        int countOfFieldsAllCountries = jsonPath.getInt("RestResponse.result.size()");
        int counter = 0;
        for(int i = 0; i < countOfFieldsAllCountries; i++){
                if((jsonPath.getString("RestResponse.result[" + i + "].name")).toLowerCase().contains(searchParameter.toLowerCase())
                        || (jsonPath.getString("RestResponse.result[" + i + "].alpha2_code")).toLowerCase().contains(searchParameter.toLowerCase())
                        || (jsonPath.getString("RestResponse.result[" + i + "].alpha3_code")).toLowerCase().contains(searchParameter.toLowerCase())){
                    counter++;
                }
        }

        assertEquals(countOfFields, counter);
    }

}
