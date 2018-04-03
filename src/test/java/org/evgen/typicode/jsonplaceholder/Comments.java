package org.evgen.typicode.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Comments {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/comments/";
    }

    @ParameterizedTest
    @MethodSource("getIdComments")
    public void commentIsNotEmpty(String idComment) {

        JsonPath jsonPath = HelperTest.getJsonPath(idComment);

        String bodyOfComment = jsonPath.getString("body");
        assertNotEquals(0, bodyOfComment.replaceAll("\\s+", "").length(), "Empty comment found");
    }

    public static List<String> getIdComments() {
        return HelperTest.getAllParametersForTestComments();
    }

}
