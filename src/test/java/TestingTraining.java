import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestingTraining {

    @BeforeAll
    public void setUP(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


    }

    @Test
    public void checkGetRequest(){
            given()
            .accept(ContentType.JSON)
            .when()
                    .get("/posts/2")
            .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

            String s = given()
                    .accept(ContentType.JSON)
                    .when()
                        .get("/users")
                    .thenReturn()
                        .asString();
        System.out.println(s);
//        JsonPath json = new JsonPath(s);
//        int userId = json.getInt("userId");
//        assertEquals(1, userId);



    }

}
