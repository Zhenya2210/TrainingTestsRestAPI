package services.groupkt.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class JsonResponseSearch {

    private JsonResponseSearch(){}

    public static JsonPath jsonPathSearch(String allCountries){
        String json = given().
                accept(ContentType.JSON).
                when().
                    get(allCountries).
                thenReturn().
                    asString();

        JsonPath jsonPath = new JsonPath(json);

        return jsonPath;
    }

    public static JsonPath jsonPathSearch(String baseURI, String searchParameter){
        String json = given().
                accept(ContentType.JSON).
                    pathParam("text", searchParameter).
                when().
                    get(baseURI).
                thenReturn().
                    asString();

        JsonPath jsonPath = new JsonPath(json);

        return jsonPath;
    }


}
