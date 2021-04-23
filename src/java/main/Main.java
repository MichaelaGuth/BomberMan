package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.images.ImageLoader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.Constants.FXML_MENU;
import static main.Constants.SCENE_HEIGHT;
import static main.Constants.SCENE_WIDTH;
import static main.images.ImagePaths.ICON;

public class Main extends Application {

    private static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(FXML_MENU));      // menu.fxml

            primaryStage.setTitle("BomberMan");
            primaryStage.getIcons().add(ImageLoader.loadImage(ICON));
            primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            primaryStage.setResizable(false);
            primaryStage.show();

            LOGGER.log(Level.INFO, "File named: menu.fxml has been successfully loaded.");

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "File named: menu.fxml could not be loaded." );
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
