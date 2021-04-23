package main.score;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.game.player.Player;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 23. 4. 2020
 * Time: 18:32
 */
public class Score {
    private static Logger LOGGER = Logger.getLogger(Score.class.getName());

    private String fileName;

    public static Score SAVED_SCORE = new Score();

    public Score() {
        this.fileName = "score.txt";
    }

    public Score(String filename) {
        this.fileName = filename;
    }

    /**
     * Saves score to our text file.
     * @param player The Player that has the score to save.
     */
    public void saveHighScore(Player player) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, true));

            // to get current date and time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();

            // <username> (<date> <time>): <score>
            writer.println(player.getUsername() + "(" + dtf.format(now) + "): " +  player.getScore());
            writer.close();

            LOGGER.log(Level.INFO, "Score has been successfully saved.");

        } catch (IOException e) { //exception for FileWriter
            LOGGER.log(Level.WARNING, "Score could not be saved. File not found.");
        }
    }

    /**
     * Loads already saved score in our text file.
     * @return The loaded score in ArrayList with String
     */
     ObservableList<ScoreItem> loadScore() {
        ObservableList<ScoreItem> list = FXCollections.observableArrayList();
        if (!(new File(fileName)).exists()) {
            return list;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

            String userData;

            while ((userData = reader.readLine()) != null) {
                String[] userDataSplit = splitLine(userData);
                String username = userDataSplit[0];
                int score = Integer.parseInt(userDataSplit[1]);

                list.add(new ScoreItem(score, username));
            }

            reader.close();

            LOGGER.log(Level.INFO, "Score has been successfully loaded.");
            
        } catch (IOException e) { //exception for FileInputStream()
            LOGGER.log(Level.SEVERE, "Score could not be loaded. File not found.");
        }

        return list;
    }

    /**
     * Splits given line in specified place (": ").
     * @param line The given line in String.
     * @return The String array with line split.
     */
    String[] splitLine(String line) {
        return line.split(": ");
    }

    String getFileName() {
        return fileName;
    }
}
