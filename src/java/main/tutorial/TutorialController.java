package main.tutorial;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.images.ImageLoader;
import main.images.ImagePaths;

import static main.Constants.FXML_MENU;
import static main.Utils.changeStage;
import static main.images.ImagePaths.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 5. 5. 2020
 * Time: 11:10
 */

public class TutorialController {
    public ImageView backButton;
    public ImageView tutorial;

    @FXML
    public void initialize() {
        backButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
        tutorial.setImage(ImageLoader.loadImage(TUTORIAL));
    }

    /**
     * Changes backButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseExited(MouseEvent mouseEvent) {
        backButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
    }

    /**
     * Changes backButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseEntered(MouseEvent mouseEvent) {
        backButton.setImage(ImageLoader.loadImage(ImagePaths.BACK_BUTTON_CLICK));
    }

    /**
     * Changes scene from Tutorial to Menu.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseClicked(MouseEvent mouseEvent) {
        changeStage(FXML_MENU);
    }
}
