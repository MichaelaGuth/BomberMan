package main;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 23. 4. 2020
 * Time: 16:43
 */
public class Utils {
    private static Logger LOGGER = Logger.getLogger(Utils.class.getName());

    /**
     * Changes scene to scene in given fxml file.
     * @param fxmlFileName The given fxml file name.
     */
    public static void changeStage(String fxmlFileName) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlFileName));    //načtení popisu scény
            Main.stage.setScene(new Scene(root, 922, 722));                 //vytvoření scény a nastavení zobrazení
            Main.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                }
            });
            Main.stage.show();
            LOGGER.log(Level.INFO, "File named: " + fxmlFileName + " has been successfully loaded.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "File named: " + fxmlFileName + " could not be loaded.");
            Main.stage.close();
        }
    }

}
