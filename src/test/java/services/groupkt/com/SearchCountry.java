package services.groupkt.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchCountry {

    String baseURI = "http://services.groupkt.com/country/search?text={text}";

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

        String json = given().
                    accept(ContentType.JSON).
                    pathParam("text", searchParameter).
                    when().
                        get(baseURI).
                    thenReturn().
                        asString();

        JsonPath jsonPath = new JsonPath(json);
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

        String json = given().
                        accept(ContentType.JSON).
                            pathParam("text", searchParameter).
                        when().
                            get(baseURI).
                        thenReturn().
                            asString();

        JsonPath jsonPath = new JsonPath(json);
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


}
