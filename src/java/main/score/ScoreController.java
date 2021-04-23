package main.score;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.images.ImageLoader;
import main.images.ImagePaths;

import static main.Constants.FXML_MENU;
import static main.Utils.changeStage;
import static main.images.ImagePaths.BACK_BUTTON;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 26. 3. 2020
 * Time: 12:21
 */
public class ScoreController {
    public ImageView backButton;
    public TableView scoreBoard;

    public TableColumn rank;
    public TableColumn usernames;
    public TableColumn scores;

    public static Score score = new Score();

    @FXML
    public void initialize() {
        setImages();
        setScoreBoard();
        loadScore();
    }

    /**
     * Sets the scoreBoard.
     */
    public void setScoreBoard() {
        rank.setCellValueFactory(new PropertyValueFactory("rank"));
        usernames.setCellValueFactory(new PropertyValueFactory("username"));
        scores.setCellValueFactory(new PropertyValueFactory("score"));
    }

    /**
     * Loads score to the scoreBoard.
     */
    public void loadScore() {
        ObservableList<ScoreItem> list = score.loadScore();
        SortedList<ScoreItem> sortedlist = new SortedList<ScoreItem>(list,
                (ScoreItem item1, ScoreItem item2) ->
                {
                    return item2.getScore()-item1.getScore();
                });

        for (int i = 0; i < sortedlist.size(); i++) {
            sortedlist.get(i).setRank(i+1);
        }
        scoreBoard.setItems(sortedlist);
    }

    /**
     * Sets images to the scene.
     */
    public void setImages() {
        backButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
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
     * Changes scene from Score to Menu.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseClicked(MouseEvent mouseEvent) {
        changeStage(FXML_MENU);
    }
}
