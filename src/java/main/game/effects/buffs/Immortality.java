package main.game.effects.buffs;

import main.game.GameBoard;
import main.game.effects.Effect;
import main.game.player.Player;
import main.images.ImageLoader;

import java.util.Timer;
import java.util.TimerTask;

import static main.images.ImagePaths.IMMORTALITY;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 3. 6. 2020
 * Time: 14:48
 */
public class Immortality extends Buff {
    private static final int DURATION = 5;

    public Immortality() {
        super(ImageLoader.loadImage(IMMORTALITY));
    }

    @Override
    public void activate(Player player, GameBoard gameBoard) {
        player.setImmune(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.setImmune(false);
            }
        }, DURATION * 1000);

        gameBoard.removeEffect(this);
    }

    @Override
    public Effect copyEffect() {
        return new Immortality();
    }
}
