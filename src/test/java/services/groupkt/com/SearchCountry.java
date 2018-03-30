package services.groupkt.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchCountry {

    String baseURI = "http://services.groupkt.com/country/search?text={text}";

//    @BeforeAll
//    public void setUP() {
//        RestAssured.baseURI = "http://services.groupkt.com/country";
//    }

    @ParameterizedTest
    @ValueSource(strings = {"un"})
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
    @ValueSource(strings = {"un"})
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
}
