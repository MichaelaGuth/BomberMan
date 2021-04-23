package main.images;

import javafx.scene.image.Image;

import java.io.File;

import static main.Constants.FXML_MENU;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 26. 3. 2020
 * Time: 12:21
 */
public class ImageLoader {
    /**
     * Loads image from given file.
     * @param filePath The name of the file.
     * @return The Image, that has been loaded.
     *         When file is not found, return
     */
    public static Image loadImage(String filePath) {
        try {
            return new Image(ImageLoader.class.getResource(filePath).toString());
        } catch (NullPointerException e) {
            System.err.println("Image not found.");
            return null;
        }
    }
}
