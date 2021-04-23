package main.game.player;

import main.JavaFXThreadingRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 4. 6. 2020
 * Time: 21:22
 */
public class GameCharacterTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void createListOfGameCharacters() throws Exception {
        LinkedList<GameCharacter> list = GameCharacter.createListOfGameCharacters();
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(GameCharacter.RED_CHARACTER, list.get(0));
        Assert.assertEquals(GameCharacter.BLACK_CHARACTER, list.get(1));
        Assert.assertEquals(GameCharacter.BLUE_CHARACTER, list.get(2));
        Assert.assertEquals(GameCharacter.WHITE_CHARACTER, list.get(3));
    }

    @Test
    public void findGameCharacter() throws Exception {
        Assert.assertEquals(GameCharacter.RED_CHARACTER, GameCharacter.findGameCharacter("Red"));
        Assert.assertEquals(GameCharacter.BLUE_CHARACTER, GameCharacter.findGameCharacter("Blue"));
        Assert.assertEquals(GameCharacter.BLACK_CHARACTER, GameCharacter.findGameCharacter("Black"));
        Assert.assertEquals(GameCharacter.WHITE_CHARACTER, GameCharacter.findGameCharacter("White"));
        Assert.assertEquals(null, GameCharacter.findGameCharacter("jkhvfhws"));
    }

}