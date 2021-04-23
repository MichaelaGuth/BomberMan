package main.game.effects;

import main.JavaFXThreadingRule;
import main.game.effects.buffs.LifeDown;
import main.game.effects.buffs.LifeUp;
import main.images.ImagePaths;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 4. 6. 2020
 * Time: 21:32
 */
public class EffectsTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    Effects effects;
    Effect lifeUp;
    Effect lifeDown;
    Effect explosion;

    @Before
    public void setUp() throws Exception {
        effects = new Effects();
        lifeUp = new LifeUp();
        lifeDown = new LifeDown();
        explosion = new Explosion(ImagePaths.FlameType.FLAME_START,1, 1);
        effects.getList().add(lifeUp);
        effects.getList().add(explosion);
    }

    @Test
    public void add() throws Exception {
        Assert.assertEquals(2, effects.getList().size());
        effects.add(lifeDown);
        Assert.assertEquals(lifeDown, effects.getList().get(2));
        Assert.assertEquals(3, effects.getList().size());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(2, effects.getList().size());
        Assert.assertEquals(lifeUp, effects.get(0));
        Assert.assertEquals(explosion, effects.get(1));
    }

    @Test
    public void put() throws Exception {
        Assert.assertEquals(lifeUp, effects.get(0));
        Assert.assertEquals(2, effects.getList().size());
        effects.put(0, lifeDown);
        Assert.assertEquals(lifeDown, effects.get(0));
        Assert.assertEquals(lifeUp, effects.get(1));
        Assert.assertEquals(explosion, effects.get(2));
        Assert.assertEquals(3, effects.getList().size());
    }

    @Test
    public void remove1() throws Exception {
        Assert.assertEquals(2, effects.getList().size());
        effects.remove(0);
        Assert.assertEquals(1, effects.getList().size());
        Assert.assertEquals(explosion, effects.get(0));
    }

    @Test
    public void remove2() throws Exception {
        Assert.assertEquals(2, effects.getList().size());
        effects.remove(lifeUp);
        Assert.assertEquals(1, effects.getList().size());
        Assert.assertEquals(explosion, effects.get(0));
    }

    @Test
    public void isEmpty() throws Exception {
        assertEquals(false, effects.isEmpty());
        effects.remove(0);
        effects.remove(0);
        assertEquals(true, effects.isEmpty());
    }

    @Test
    public void getBuff() throws Exception {
        assertEquals(lifeUp, effects.getBuff());
        effects.remove(lifeUp);
        assertEquals(null, effects.getBuff());
    }

}