package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Photos {

    String allPhotos = "https://jsonplaceholder.typicode.com/photos";

    @Test
    public void checkSizeOfPhoto() throws IOException {
        String json = given().
                        accept(ContentType.JSON).
                        when().
                            get(allPhotos).
                        thenReturn().
                        asString();

        JsonPath jsonPath = new JsonPath(json);

        int countOfImages = jsonPath.getInt("size()");

        for(int i = 0; i < countOfImages; i++) {
            URL url = new URL(jsonPath.getString("url[" + i + "]"));
            BufferedImage image = ImageIO.read(url);
            int width = image.getWidth();
            int height = image.getHeight();
            int idPhoto = jsonPath.getInt("id[" + i + "]");

            assertEquals(600, width, "Ширина фото номер " + idPhoto + " не соответствует требованиям.");
            assertEquals(600, height, "Высота фото номер " + idPhoto + " не соответствует требованиям.");
        }
    }
}
