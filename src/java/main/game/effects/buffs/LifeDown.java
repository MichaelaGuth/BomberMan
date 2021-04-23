package main.game.effects.buffs;

import main.game.GameBoard;
import main.game.effects.Effect;
import main.game.player.Player;
import main.images.ImageLoader;

import static main.images.ImagePaths.LIFE_DOWN;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 3. 6. 2020
 * Time: 14:48
 */
public class LifeDown extends Buff {
    public LifeDown() {
        super(ImageLoader.loadImage(LIFE_DOWN));
    }

    @Override
    public Effect copyEffect() {
        return new LifeDown();
    }

    @Override
    public void activate(Player player, GameBoard gameBoard) {
        player.minusOneLife();
        gameBoard.removeEffect(this);
    }

}
