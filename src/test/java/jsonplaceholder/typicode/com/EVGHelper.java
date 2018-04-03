package jsonplaceholder.typicode.com;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
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
                            get().
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

    public static JsonPath getJsonPath(String parameterRequest){

        String json = given().
                    accept(ContentType.JSON).
                when().
                    get(parameterRequest).
                thenReturn().
                    asString();

        JsonPath jsonPath = new JsonPath(json);

        return jsonPath;
    }


    public static List<String> getParametersForTestPhoto(){
        String json = given().
                    accept(ContentType.JSON).
                when().
                    get("https://jsonplaceholder.typicode.com/photos").
                thenReturn().
                    asString();
        JsonPath jsonPath = new JsonPath(json);
        int quantityOfPhotos = jsonPath.getInt("size()");

        List<String> listIdPhotos = new ArrayList<>();

        for(int i = 0; i < quantityOfPhotos; i++){
            listIdPhotos.add(jsonPath.getString("id[" + i + "]"));
        }
        return listIdPhotos;
    }

    public static BufferedImage getPhoto(String idPhoto) throws IOException {

        String json = given().
                    accept(ContentType.JSON).
                when().
                    get(idPhoto).
                thenReturn().
                    asString();

        JsonPath jsonPath = new JsonPath(json);

        URL url = new URL(jsonPath.getString("url"));
        BufferedImage image = ImageIO.read(url);

        return image;
    }
}
