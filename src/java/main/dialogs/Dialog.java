package main.dialogs;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.Main;
import main.images.ImageLoader;

import java.io.IOException;

import static main.images.ImagePaths.ICON;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 23. 4. 2020
 * Time: 21:08
 */
public class Dialog {

    public enum DialogType {
        GAME_CONFIG("/fxml/gameConfigDialog.fxml", 500, 534, "Game Configuration"), GAME_OVER("/fxml/gameOverDialog.fxml", 400, 400, "Game Over");

        private String fxmlFile;
        private int width;
        private int height;
        private String title;

        DialogType(String fxmlFile, int width, int height, String title) {
            this.fxmlFile = fxmlFile;
            this.width = width;
            this.height = height;
            this.title = title;
        }

        public String getFxmlFile() {
            return fxmlFile;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getTitle() {
            return title;
        }
    }

    private static Stage dialogStage;

    /**
     * Raise dialog window from given fxml file.
     * @param dialogType The type of dialog.
     */
    public static void raiseDialog(DialogType dialogType) {
        try {
            dialogStage = new Stage(StageStyle.DECORATED);
            Parent root = FXMLLoader.load(Dialog.class.getResource(dialogType.getFxmlFile()));
            dialogStage.setScene(new Scene(root, dialogType.getWidth(), dialogType.getHeight()));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.stage.getScene().getWindow());
            dialogStage.setResizable(false);
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                }
            });
            dialogStage.setTitle(dialogType.getTitle());
            dialogStage.getIcons().add(ImageLoader.loadImage(ICON));
            dialogStage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("File " + dialogType.getFxmlFile() + " not found.");
        }

    }

    /**
     * Close currently active dialog.
     */
    protected static void closeDialog() {
        dialogStage.close();
        dialogStage = null;
    }
}
