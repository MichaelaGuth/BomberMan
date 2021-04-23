package main.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.game.player.Player;
import main.images.ImageLoader;

import static main.Constants.FXML_MENU;
import static main.Utils.changeStage;
import static main.dialogs.Dialog.closeDialog;
import static main.images.ImagePaths.GAME_OVER;

import static main.game.GameController.game;
import static main.score.Score.SAVED_SCORE;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 16. 5. 2020
 * Time: 20:49
 */
public class GameOverDialogController {

    public ImageView GameOver;

    public Label Username1;
    public Label Score1;

    public Label Username2;
    public Label Score2;

    public Button saveHighScoreButton;
    public Button backToMenuButton;

    private boolean scoreSaved;

    @FXML
    public void initialize() {
        GameOver.setImage(ImageLoader.loadImage(GAME_OVER));

        if (game.getNumOfPlayers() == 1) {
            Player p1 = game.getRealPlayers().get(0);
            Username1.setText(p1.getUsername() + ":");
            Score1.setText("" + p1.getScore());
        } else {
            Player p1 = game.getRealPlayers().get(0);
            Player p2 = game.getRealPlayers().get(1);

            Username1.setText(p1.getUsername() + ":");
            Score1.setText(p1.getScore() + "");

            Username2.setText(p2.getUsername() + ":");
            Score2.setText(p2.getScore() + "");
        }

        scoreSaved = false;
    }

    /**
     * Saves score on highScoreButton's click.
     * @param mouseEvent not used
     */
    public void saveHighScoreButtonClick(MouseEvent mouseEvent) {
        if (!scoreSaved) {
            SAVED_SCORE.saveHighScore(game.getRealPlayers().get(0));
            if (game.getNumOfPlayers() == 2) {
                SAVED_SCORE.saveHighScore(game.getRealPlayers().get(1));
            }
            scoreSaved = true;
    }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score saved");
        alert.setHeaderText(null);
        alert.setContentText("The score has been successfully saved! Check it out in our High Score section.");

        alert.showAndWait();
    }

    /**
     * Changes scene from Game to Menu and closes the dialog.
     * @param mouseEvent not used
     */
    public void backToMenuButtonClick(MouseEvent mouseEvent) {
        changeStage(FXML_MENU);
        game = null;
        closeDialog();
    }
}
