package main.game.player;

import main.Constants;
import main.JavaFXThreadingRule;
import main.game.Game;
import main.game.GameBoard;
import main.game.effects.Bomb;
import main.game.effects.Effect;
import main.game.effects.buffs.BombDegrade;
import main.game.effects.buffs.BombUpgrade;
import main.game.effects.buffs.LifeDown;
import main.game.effects.buffs.LifeUp;
import main.images.ImageLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static main.Constants.*;
import static main.game.player.GameCharacter.BLUE_CHARACTER;
import static main.game.player.GameCharacter.RED_CHARACTER;
import static main.game.player.GameCharacter.createListOfGameCharacters;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 4. 6. 2020
 * Time: 15:36
 */
public class PlayerTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    Player p1;
    Player p2;
    Player p3;

    Game game;

    @Before
    public void setUp() throws Exception {
        p3 = new AI(BLUE_CHARACTER, 1, 1);

        game = new Game(0,1,new GameBoard(),RED_CHARACTER,BLUE_CHARACTER,"Kimiko","FengLee");
        p1 = game.getPlayer(1);
        p2 = game.getPlayer(2);
        p2.setLives(0);
        while (p2.getFlameLength() < MAX_FLAME_LENGTH) {
            p2.increaseFlameLength();
        }
    }

    @Test
    public void setStartParameters() throws Exception {
        Assert.assertEquals(MIN_FLAME_LENGTH, p1.getFlameLength());
        Assert.assertEquals(LIFE_COUNT, p1.getLives());
        Assert.assertEquals(false, p1.isImmune());
        Assert.assertEquals(PlayerPose.LOOK_DOWN, p1.getCharacter().getCurrPose());
        Assert.assertEquals(Player.PlayerStartPosition.PLAYER_1.getX(), p1.getPositionX());
        Assert.assertEquals(Player.PlayerStartPosition.PLAYER_1.getY(), p1.getPositionY());
        //Assert.assertEquals(ImageLoader.loadImage(PlayerPose.LOOK_DOWN.getImageName()), p1.getImage());
    }

    @Test
    public void isAlive() throws Exception {
        Assert.assertEquals(true, p1.isAlive());
        Assert.assertEquals(false, p2.isAlive());
    }

    @Test
    public void isAI() throws Exception {
        Assert.assertEquals(true, p3.isAI());
        Assert.assertEquals(false, p1.isAI());
        Assert.assertEquals(false, p2.isAI());
    }

    @Test
    public void minusOneLife() throws Exception {
        p1.minusOneLife();
        Assert.assertEquals(LIFE_COUNT-1, p1.getLives());

        p2.minusOneLife();
        Assert.assertEquals(0, p2.getLives());
    }

    @Test
    public void plusOneLife() throws Exception {
        p1.plusOneLife();
        Assert.assertEquals(LIFE_COUNT, p1.getLives());

        p2.plusOneLife();
        Assert.assertEquals(1, p2.getLives());
    }

    @Test
    public void addScore() throws Exception {
        p1.addScore(50);
        Assert.assertEquals(50, p1.getScore());
    }

    @Test
    public void increaseFlameLength() throws Exception {
        p1.increaseFlameLength();
        Assert.assertEquals(MIN_FLAME_LENGTH+1, p1.getFlameLength());

        p2.increaseFlameLength();
        Assert.assertEquals(MAX_FLAME_LENGTH, p2.getFlameLength());
    }

    @Test
    public void decreaseFlameLength() throws Exception {
        p1.decreaseFlameLength();
        Assert.assertEquals(MIN_FLAME_LENGTH, p1.getFlameLength());

        p1.decreaseFlameLength();
        Assert.assertEquals(MIN_FLAME_LENGTH, p1.getFlameLength());
    }

    @Test
    public void move() throws Exception {
        int prevPosX = game.getPlayer(1).getPositionX();
        int prevPosY = game.getPlayer(1).getPositionY();

        if (game.getPlayer(1).move(Constants.Direction.RIGHT, game)) {
            Assert.assertEquals(prevPosX + 1, game.getPlayer(1).getPositionX());
        } else {
            Assert.assertEquals(prevPosX, game.getPlayer(1).getPositionX());
        }
        Assert.assertEquals(prevPosY, game.getPlayer(1).getPositionY());

        prevPosX = game.getPlayer(1).getPositionX();
        prevPosY = game.getPlayer(1).getPositionY();

        if (game.getPlayer(1).move(Constants.Direction.UP, game)) {
            Assert.assertEquals(prevPosY - 1, game.getPlayer(1).getPositionY());
        } else {
            Assert.assertEquals(prevPosY, game.getPlayer(1).getPositionY());
        }
        Assert.assertEquals(prevPosX, game.getPlayer(1).getPositionX());
    }

    @Test
    public void placeBomb() throws Exception {
        p1.placeBomb(game);
        for (Effect e : game.getEffects()[p1.getPositionX()][p1.getPositionY()].getList()) {
            if (e.getOwnerNumber() == 1) {
                Assert.assertEquals(1, e.getOwnerNumber());
            }
        }
    }

    @Test
    public void updatePlayerImmunity() throws Exception {
        p1.placeBomb(game);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int lives = p1.getLives();
                p1.updatePlayerImmunity(game.getEffects(), game.getAlivePlayers());
                Assert.assertEquals(true, p1.isImmune());
                Assert.assertEquals(true, p1.isAlive());
                if (lives > 0) lives--;
                Assert.assertEquals(lives, p1.getLives());
                //Assert.assertEquals(SCORE_HIT_BY_MYSELF, p1.getScore());
            }
        }, 2000);

    }

    @Test
    public void pickBuff() throws Exception {
        game.getEffects()[p1.getPositionX()][p1.getPositionY()].add(new LifeDown());
        int tmp = p1.getLives();
        if (tmp > 0) tmp--;
        p1.pickBuff(game.getGameBoard());
        Assert.assertEquals(tmp, p1.getLives());

        game.getEffects()[p1.getPositionX()][p1.getPositionY()].add(new LifeUp());
        tmp = p1.getLives();
        if (tmp < 5) tmp++;
        p1.pickBuff(game.getGameBoard());
        Assert.assertEquals(tmp, p1.getLives());

        for (int i = 0; i < MAX_FLAME_LENGTH + 1; i++) {
            game.getEffects()[p1.getPositionX()][p1.getPositionY()].add(new BombUpgrade());
            tmp = p1.getFlameLength();
            if (tmp < MAX_FLAME_LENGTH) tmp++;
            p1.pickBuff(game.getGameBoard());
            Assert.assertEquals(tmp, p1.getFlameLength());
        }

        for (int i = 0; i < MAX_FLAME_LENGTH + 1 ; i++) {
            game.getEffects()[p1.getPositionX()][p1.getPositionY()].add(new BombDegrade());
            tmp = p1.getFlameLength();
            if (tmp > MIN_FLAME_LENGTH) tmp--;
            p1.pickBuff(game.getGameBoard());
            Assert.assertEquals(tmp, p1.getFlameLength());
        }
    }

}