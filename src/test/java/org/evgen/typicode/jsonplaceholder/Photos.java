package org.evgen.typicode.jsonplaceholder;

import io.restassured.RestAssured;
import org.evgen.HelperTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Photos {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/photos/";
    }

    /**
     * Dangerous! very long test :)
     */
    @ParameterizedTest
    @MethodSource("getIdPhotos")
    public void widthHeightOfPhoto(String idPhoto) throws IOException {

        BufferedImage image = HelperTest.getPhoto(idPhoto);
        int width = image.getWidth();
        int height = image.getHeight();

        assertEquals(600, width, "Width photo number " + idPhoto + " doesn't meet the requirements.");
        assertEquals(600, height, "Height photo number " + idPhoto + " doesn't meet the requirements.");

    }

    public static List<String> getIdPhotos() {
        return HelperTest.getIdPhotos();
    }

}