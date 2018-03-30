package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonResponse {

    private JsonResponse(){}

    @Test
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
