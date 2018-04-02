package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class EVGHelper {

    private EVGHelper(){}

    public static Stream<String> userIDs(){
        String jsonUsers = given().
                        accept(ContentType.JSON).
                        when().
                            get("https://jsonplaceholder.typicode.com/users").
                        thenReturn().
                            asString();
        JsonPath jsonPath = new JsonPath(jsonUsers);

        List<String> userIDsList = new ArrayList<>();

        int quantityOfFields = jsonPath.getInt("size()");

        for(int i = 0; i < quantityOfFields; i++){

            userIDsList.add(jsonPath.getString("id[" + i + "]"));
        }

        Stream<String> userIDsStream = userIDsList.stream();

        return userIDsStream;
    }

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
