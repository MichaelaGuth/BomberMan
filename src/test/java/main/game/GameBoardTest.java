package main.game;

import main.JavaFXThreadingRule;
import main.game.blocks.Crate;
import main.game.effects.Effect;
import main.game.effects.buffs.LifeUp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 4. 6. 2020
 * Time: 20:26
 */
public class GameBoardTest {
    GameBoard gameBoard;
    Effect effect;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {
        gameBoard = new GameBoard();
        effect = new LifeUp();
    }

    @Test
    public void addEffect() throws Exception {
        Assert.assertEquals(true, gameBoard.getEffects()[1][1].isEmpty());
        gameBoard.addEffect(effect, 1, 1);
        Assert.assertEquals(false, gameBoard.getEffects()[1][1].isEmpty());
    }

    @Test
    public void removeEffect() throws Exception {
        gameBoard.addEffect(effect, 1, 1);
        Assert.assertEquals(false, gameBoard.getEffects()[1][1].isEmpty());
        gameBoard.removeEffect(effect);
        Assert.assertEquals(true, gameBoard.getEffects()[1][1].isEmpty());
    }

    @Test
    public void destroyBlock() throws Exception {
        Assert.assertEquals(true, gameBoard.getBlocks()[1][1].isPassable());
        gameBoard.getBlocks()[1][1] = new Crate();
        Assert.assertEquals(false, gameBoard.getBlocks()[1][1].isPassable());
        gameBoard.destroyBlock(1,1);
        Assert.assertEquals(true, gameBoard.getBlocks()[1][1].isPassable());
    }

}