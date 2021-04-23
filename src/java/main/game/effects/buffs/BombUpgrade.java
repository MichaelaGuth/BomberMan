package main.game.effects.buffs;

import main.game.GameBoard;
import main.game.effects.Effect;
import main.game.player.Player;
import main.images.ImageLoader;

import static main.images.ImagePaths.BOMB_UPGRADE;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 3. 6. 2020
 * Time: 14:48
 */
public class BombUpgrade extends Buff {
    public BombUpgrade() {
        super(ImageLoader.loadImage(BOMB_UPGRADE));
    }

    @Override
    public void activate(Player player, GameBoard gameBoard) {
        player.increaseFlameLength();
        gameBoard.removeEffect(this);
    }

    @Override
    public Effect copyEffect() {
        return new BombUpgrade();
    }
}
