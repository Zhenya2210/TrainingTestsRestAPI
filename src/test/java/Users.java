import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Users {

    @BeforeAll
    public void setUP(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void checkGetUsersEmail(String parameterRequest){
        given()
                .accept(ContentType.JSON)
                .when()
                    .get("/users/" + parameterRequest)
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK);

        String json = given()
                .accept(ContentType.JSON)
                .when()
                    .get("/users/" + parameterRequest)
                .thenReturn()
                    .asString();

        JsonPath jsonPath = new JsonPath(json);
        String email = jsonPath.getString("email");

        Pattern pattern = Pattern.compile("^((\\w|[-+])+(\\.[\\w-]+)*@[\\w-]+((\\.[\\d\\p{Alpha}]+)*(\\.\\p{Alpha}{2,})*)*)$");
        Matcher matcher = pattern.matcher(email);
        String validity = "";
        while (matcher.find()){
            validity = email.substring(matcher.start(), matcher.end());
        }

        assertNotNull(email);
        assertSame(email, validity); //check e-mail

    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void checkGetUsersID(String parameterRequest){

        String json = given()
                .accept(ContentType.JSON)
                .when()
                .get("/users/" + parameterRequest)
                .thenReturn()
                .asString();

        JsonPath jsonPath = new JsonPath(json);
        int id = jsonPath.getInt("id");

        assertNotNull(id);
        assertEquals(Integer.parseInt(parameterRequest), id); //check id

    }
}
