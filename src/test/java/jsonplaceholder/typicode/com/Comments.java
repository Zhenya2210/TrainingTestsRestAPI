package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Comments {
    String uriAllComments = "https://jsonplaceholder.typicode.com/comments";
    String uriCommentsWithParameter = "https://jsonplaceholder.typicode.com/comments?postId={postID}";

    @Test
    public void commentIsNotEmpty(){
        String json = given().
                        accept(ContentType.JSON).
                    when().
                        get(uriAllComments).
                    thenReturn().
                        asString();

        JsonPath jsonPath = new JsonPath(json);
        int countOfComments = jsonPath.getInt("size()");

        String bodyOfcomment;

        for(int i = 0; i < countOfComments; i++){
            bodyOfcomment = jsonPath.getString("body[" + i + "]");
            assertNotEquals(0, bodyOfcomment.replaceAll("\\s+","").length(), "Найден пустой комментарий");
        }

    }

}
