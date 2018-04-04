package org.evgen;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class HelperTest {

    private HelperTest() {
    }

    public static Stream<String> getAllUserIDs() {

        JsonPath jsonPath = HelperTest.getJsonPath("https://jsonplaceholder.typicode.com/users/");

        return jsonPath.getList("id", String.class).stream();

    }

    public static JsonPath getJsonPath(String path, Object... parametersPath) {

        return given().
                    accept(ContentType.JSON).
                when().
                    get(path, parametersPath).
                thenReturn().
                    jsonPath();
    }


    public static List<String> getIdPhotos() {

        JsonPath jsonPath = HelperTest.getJsonPath("https://jsonplaceholder.typicode.com/photos/");

        return jsonPath.getList("id", String.class);
    }

    public static List<String> getAllParametersForTestComments() {

        JsonPath jsonPath = HelperTest.getJsonPath("https://jsonplaceholder.typicode.com/comments/");

        return jsonPath.getList("id", String.class);

    }

    public static BufferedImage getPhoto(String idPhoto) throws IOException {

        JsonPath jsonPath = HelperTest.getJsonPath(idPhoto);

        URL url = new URL(jsonPath.getString("url"));
        return ImageIO.read(url);

    }

    public static int getNumericalValueFromMessage(String message) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(message);
        int valueOfMessage = 0;
        while (matcher.find()) {
            valueOfMessage = Integer.parseInt(message.substring(matcher.start(), matcher.end()));
        }
        return valueOfMessage;
    }
}
