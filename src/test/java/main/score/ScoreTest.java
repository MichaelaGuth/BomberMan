package main.score;

import main.JavaFXThreadingRule;
import main.game.player.GameCharacter;
import main.game.player.Player;
import org.junit.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 4. 6. 2020
 * Time: 19:45
 */
public class ScoreTest {
    Score score;
    Player p1;
    File f;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {
        score = new Score("testScore.txt");
        p1 = new Player(GameCharacter.RED_CHARACTER, "Guss", 1);
        p1.setScore(300);
        f = new File(score.getFileName());
        if (f.exists()) {
            f.delete();
            f.createNewFile();
        }

    }

    @Test
    public void saveHighScore() throws Exception {
        score.saveHighScore(p1);
        Assert.assertEquals(true, f.exists());
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        String[] data = line.split(": ");
        String[] data2 = data[0].split("\\(");
        Assert.assertEquals(p1.getUsername(), data2[0]);
        Assert.assertEquals(p1.getScore() + "", data[1]);
        br.close();
    }

    @Test
    public void splitLine() throws Exception {
        String line = "Kimiko: 1514265";
        String[] data = score.splitLine(line);
        Assert.assertEquals("Kimiko", data[0]);
        Assert.assertEquals("1514265", data[1]);
    }
}