package main.menu;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import main.images.ImageLoader;

import static main.Utils.changeStage;
import static main.dialogs.Dialog.raiseDialog;
import static main.images.ImagePaths.*;
import static main.Constants.FXML_SCORE;
import static main.Constants.FXML_TUTORIAL;


/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 26. 3. 2020
 * Time: 12:21
 */

public class MenuController {

    public ImageView ScoreButton;
    public ImageView ExitButton;
    public ImageView BomberMan;
    public ImageView Background;
    public ImageView TutorialButton;
    public ImageView NewGameButton;

    @FXML
    public void initialize() {
        setImages();
    }

    /**
     * Sets images to scene.
     */
    private void setImages() {
        NewGameButton.setImage(ImageLoader.loadImage(NEW_GAME_BUTTON));
        ScoreButton.setImage(ImageLoader.loadImage(SCORE_BUTTON));
        ExitButton.setImage(ImageLoader.loadImage(EXIT_BUTTON));
        BomberMan.setImage(ImageLoader.loadImage(BOMBERMAN));
        TutorialButton.setImage(ImageLoader.loadImage(TUTORIAL_BUTTON));
    }


    /**
     * Closes window and terminates the application.
     * @param mouseEvent not used
     */
    public void exitButtonClick(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Changes exitButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void exitButtonOnMouseEntered(MouseEvent mouseEvent) {
        ExitButton.setImage(ImageLoader.loadImage(EXIT_BUTTON_CLICK));
    }

    /**
     * Changes exitButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void exitButtonOnMouseExited(MouseEvent mouseEvent) {
        ExitButton.setImage(ImageLoader.loadImage(EXIT_BUTTON));
    }

    /**
     * Opens dialog window for creating a new game.
     * @param mouseEvent not used
     */
    public void newGameButtonClick(MouseEvent mouseEvent) {
        raiseDialog(main.dialogs.Dialog.DialogType.GAME_CONFIG);
    }

    /**
     * Changes newGameButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void newGameButtonOnMouseEntered(MouseEvent mouseEvent) {
        NewGameButton.setImage(ImageLoader.loadImage(NEW_GAME_BUTTON_CLICK));
    }

    /**
     * Changes newGameButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void newGameButtonOnMouseExited(MouseEvent mouseEvent) {
        NewGameButton.setImage(ImageLoader.loadImage(NEW_GAME_BUTTON));
    }


    /**
     * Changes scene from Menu to Tutorial.
     * @param mouseEvent not used
     */
    public void tutorialButtonClick(MouseEvent mouseEvent) {
        changeStage(FXML_TUTORIAL);
    }

    /**
     * Changes tutorialButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void tutorialButtonOnMouseEntered(MouseEvent mouseEvent) {
        TutorialButton.setImage(ImageLoader.loadImage(TUTORIAL_BUTTON_CLICK));
    }

    /**
     * Changes tutorialButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void tutorialButtonOnMouseExited(MouseEvent mouseEvent) {
        TutorialButton.setImage(ImageLoader.loadImage(TUTORIAL_BUTTON));
    }


    /**
     * Changes scene from Menu to Score.
     * @param mouseEvent not used
     */
    public void scoreButtonClick(MouseEvent mouseEvent) {
        changeStage(FXML_SCORE);
    }

    /**
     * Changes scoreButton's image when mouse enters.
     * @param mouseEvent not used
     */
    public void scoreButtonOnMouseEntered(MouseEvent mouseEvent) {
        ScoreButton.setImage(ImageLoader.loadImage(SCORE_BUTTON_CLICK));
    }

    /**
     * Changes scoreButton's image when mouse exits.
     * @param mouseEvent not used
     */
    public void scoreButtonOnMouseExited(MouseEvent mouseEvent) {
        ScoreButton.setImage(ImageLoader.loadImage(SCORE_BUTTON));
    }
}
