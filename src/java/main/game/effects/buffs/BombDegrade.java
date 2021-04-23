package main.game.effects.buffs;

import main.game.GameBoard;
import main.game.effects.Effect;
import main.game.player.Player;
import main.images.ImageLoader;

import static main.images.ImagePaths.BOMB_DEGRADE;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 3. 6. 2020
 * Time: 14:41
 */

public class BombDegrade extends Buff {
    public BombDegrade() {
        super(ImageLoader.loadImage(BOMB_DEGRADE));
    }

    @Override
    public void activate(Player player, GameBoard gameBoard) {
        player.decreaseFlameLength();
        gameBoard.removeEffect(this);
    }

    @Override
    public Effect copyEffect() {
        return new BombDegrade();
    }
}
