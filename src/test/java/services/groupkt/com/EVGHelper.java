package services.groupkt.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class EVGHelper {

    private EVGHelper(){}

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

    public static int numericalValueFromMessage(String message){
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(message);
        int valueOfMessage = 0;
        while (matcher.find()){
            valueOfMessage = Integer.parseInt(message.substring(matcher.start(), matcher.end()));
        }

        return valueOfMessage;
    }


}
