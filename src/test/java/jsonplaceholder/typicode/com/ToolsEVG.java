package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class ToolsEVG {

    private ToolsEVG(){}

    public static Stream<String> userIDs(){
        String jsonUsers = given().
                        accept(ContentType.JSON).
                        when().
                            get("https://jsonplaceholder.typicode.com/users").
                        thenReturn().
                            asString();
        JsonPath jsonPath = new JsonPath(jsonUsers);

        List<String> userIDsList = new ArrayList<>();

        int countOfFields = jsonPath.getInt("size()");

        String userID;

        for(int i = 0; i < countOfFields; i++){

            userIDsList.add(jsonPath.getString("id[" + i + "]"));
        }

        Stream<String> userIDsStream = userIDsList.stream();

        return userIDsStream;
    }
}
