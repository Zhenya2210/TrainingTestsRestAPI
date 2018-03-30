package services.groupkt.com;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchCountry {

    String patternRequest = "http://services.groupkt.com/country/search?text={text_to_search}";

    @ParameterizedTest
    @ValueSource(strings = {"un", "ldvmdzlm"})
    public void searchCountry(String searchParameter){
        given().
                pathParam("text_to_search", searchParameter).
            when().
                get(patternRequest).
            then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                contentType(ContentType.JSON);
    }
}
