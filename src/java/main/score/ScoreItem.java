package main.score;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 5. 5. 2020
 * Time: 11:40
 */
public class ScoreItem {
    private SimpleIntegerProperty score;
    private SimpleStringProperty username;
    private SimpleIntegerProperty rank;

    public ScoreItem(int score, String username) {
        this.score = new SimpleIntegerProperty(score);
        this.username = new SimpleStringProperty(username);
        this.rank = new SimpleIntegerProperty(0);
    }

    public int getScore() {
        return score.get();
    }

    public String getUsername() {
        return username.get();
    }

    public void setRank(int rank) {
        this.rank = new SimpleIntegerProperty(rank);
    }

    public int getRank() {
        return rank.get();
    }
}
