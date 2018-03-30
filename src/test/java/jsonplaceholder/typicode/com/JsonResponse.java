package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class JsonResponse {

    private JsonResponse(){}

    public static JsonPath jsonPathUsers(String parameterRequest){

        String json = given().
                accept(ContentType.JSON).
                when().
                    get("/users/" + parameterRequest).
                thenReturn().
                    asString();

        JsonPath jsonPath = new JsonPath(json);

        return jsonPath;
    }
}
