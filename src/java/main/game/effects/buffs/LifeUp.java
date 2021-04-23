package main.game.effects.buffs;

import main.game.GameBoard;
import main.game.effects.Effect;
import main.game.player.Player;
import main.images.ImageLoader;

import static main.images.ImagePaths.LIFE_UP;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 3. 6. 2020
 * Time: 14:41
 */
public class LifeUp extends Buff {
    public LifeUp() {
        super(ImageLoader.loadImage(LIFE_UP));
    }

    @Override
    public Effect copyEffect() {
        return new LifeUp();
    }

    @Override
    public void activate(Player player, GameBoard gameBoard) {
        player.plusOneLife();
        gameBoard.removeEffect(this);
    }

}
