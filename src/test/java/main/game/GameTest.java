package main.game;

import main.JavaFXThreadingRule;
import main.game.effects.buffs.LifeUp;
import main.game.player.GameCharacter;
import main.game.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 4. 6. 2020
 * Time: 20:38
 */
public class GameTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    Game game;
    Player p2;

    @Before
    public void setUp() throws Exception {
        game = new Game(2,1,new GameBoard(), GameCharacter.BLUE_CHARACTER, GameCharacter.RED_CHARACTER, "Kimiko", "FengLee");
        p2 = game.getAlivePlayers().get(1);
        p2.setLives(0);
        game.getDeadPlayers().add(p2);
        game.getAlivePlayers().remove(p2);
    }

    @Test
    public void getAllPlayers() throws Exception {
        List<Player> list = game.getAllPlayers();
        Assert.assertEquals(game.getNumOfPlayers() + game.getNumOfAIs(), list.size());
        for (Player p : game.getAlivePlayers()) {
            Player player = null;
            for (Player p2 : list) {
                if (p2.equals(p)) {
                    player = p2;
                }
            }
            if (player != null) list.remove(player);
        }

        for (Player p : game.getDeadPlayers()) {
            Player player = null;
            for (Player p2 : list) {
                if (p2.equals(p)) {
                    player = p2;
                }
            }
            if (player != null) list.remove(player);
        }

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getRealPlayers() throws Exception {
        List<Player> list = game.getRealPlayers();
        Assert.assertEquals(game.getNumOfPlayers(), list.size());
        for (Player p : list) {
            Assert.assertEquals(false, p.isAI());
        }
    }

    @Test
    public void getPlayer() throws Exception {
        Player p = game.getPlayer(1);
        Assert.assertEquals(1, p.getNumber());

        p = game.getPlayer(2);
        Assert.assertEquals(2, p.getNumber());

        p = game.getPlayer(3);
        Assert.assertEquals(3, p.getNumber());

        p = game.getPlayer(4);
        Assert.assertEquals(4, p.getNumber());
    }

    @Test
    public void checkIfGameOver() throws Exception {
        Player player = game.getPlayer(1);

        Assert.assertEquals(false, game.checkIfGameOver());

        game.getAlivePlayers().remove(player);
        Assert.assertEquals(true, game.checkIfGameOver());

        game.getAlivePlayers().add(player);
        Assert.assertEquals(false, game.checkIfGameOver());

        game.getAlivePlayers().remove(game.getPlayer(3));
        Assert.assertEquals(false, game.checkIfGameOver());

        game.getAlivePlayers().remove(game.getPlayer(4));
        Assert.assertEquals(true, game.checkIfGameOver());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(3 ,game.getAlivePlayers().size());
        Assert.assertEquals(1, game.getDeadPlayers().size());
        Assert.assertEquals(true, game.getPlayer(1).isAlive());
        game.getPlayer(1).setLives(0);
        game.update();
        Assert.assertEquals(2 ,game.getAlivePlayers().size());
        Assert.assertEquals(false, game.getPlayer(1).isAlive());
        Assert.assertEquals(2, game.getDeadPlayers().size());
    }

}