package org.evgen.groupkt.services;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.evgen.HelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchCountry {

    private String searchCountryURI = "http://services.groupkt.com/country/search?text={text}";
    private String allCountriesURI = "http://services.groupkt.com/country/get/all";

    @ParameterizedTest
    @ValueSource(strings = {"un", "UN", "zlqx", ""})
    public void searchStatusSC_OK(String searchParameter) {

        given().
                pathParam("text", searchParameter).
                when().
                get(searchCountryURI).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                contentType(ContentType.JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void searchCountryResult(String searchParameter) {

        JsonPath jsonPath = HelperTest.getJsonPath(searchCountryURI, searchParameter);
        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        for (int i = 0; i < quantityOfObjects; i++) {
            assertTrue((jsonPath.getString("RestResponse.result[" + i + "].name")).toLowerCase().contains(searchParameter.toLowerCase())
                    || (jsonPath.getString("RestResponse.result[" + i + "].alpha2_code")).toLowerCase().contains(searchParameter.toLowerCase())
                    || (jsonPath.getString("RestResponse.result[" + i + "].alpha3_code")).toLowerCase().contains(searchParameter.toLowerCase()));
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void valueOfMessage(String searchParameter) {

        JsonPath jsonPath = HelperTest.getJsonPath(searchCountryURI, searchParameter);

        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        String message = jsonPath.getString("RestResponse.messages");

        int numericalValueFromMessage = HelperTest.getNumericalValueFromMessage(message);

        assertEquals(quantityOfObjects, numericalValueFromMessage);
    }

    @DisplayName("Checking the search result for duplicates")
    @ParameterizedTest
    @ValueSource(strings = {"un", "ru"})
    public void dublicateOfSearchResult(String searchParameter) {

        JsonPath jsonPath = HelperTest.getJsonPath(searchCountryURI, searchParameter);

        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        HashMap<String, Integer> map = new HashMap<>();

        String nameOfCountry;

        for (int i = 0; i < quantityOfObjects; i++) {
            nameOfCountry = jsonPath.getString("RestResponse.result[" + i + "].name");
            if (!map.containsKey(nameOfCountry)) {
                map.put(nameOfCountry, 1);
            } else {
                map.put(nameOfCountry, map.get(nameOfCountry) + 1);
            }
        }

        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            int value = pair.getValue();
            assertEquals(1, value);
        }
    }

    @DisplayName("Loss check")
    @ParameterizedTest
    @ValueSource(strings = {"un", "RU", ""})
    public void searchCountryLosses(String searchParameter) {

        JsonPath jsonPath = HelperTest.getJsonPath(searchCountryURI, searchParameter);
        int quantityOfObjects = jsonPath.getInt("RestResponse.result.size()");

        jsonPath = HelperTest.getJsonPath(allCountriesURI);
        int quantityOfFieldsAllCountries = jsonPath.getInt("RestResponse.result.size()");

        int counter = 0;
        for (int i = 0; i < quantityOfFieldsAllCountries; i++) {
            if ((jsonPath.getString("RestResponse.result[" + i + "].name")).toLowerCase().contains(searchParameter.toLowerCase())
                    || (jsonPath.getString("RestResponse.result[" + i + "].alpha2_code")).toLowerCase().contains(searchParameter.toLowerCase())
                    || (jsonPath.getString("RestResponse.result[" + i + "].alpha3_code")).toLowerCase().contains(searchParameter.toLowerCase())) {
                counter++;
            }
        }
        assertEquals(quantityOfObjects, counter);
    }

    @ParameterizedTest
    @MethodSource("valueForMethodSource")
    public void searchIncorrectCountry(String searchParameter) {
        JsonPath jsonPath = HelperTest.getJsonPath(searchCountryURI, searchParameter);
        String message = jsonPath.getString("RestResponse.messages");

        assertEquals("[No matching country found for requested code [" + searchParameter + "].]", message);
    }

    public static String[] valueForMethodSource() {
        String[] valueForTest = {"8g8", "kc7"};
        return valueForTest;
    }
}
