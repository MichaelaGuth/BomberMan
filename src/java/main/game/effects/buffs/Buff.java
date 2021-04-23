package main.game.effects.buffs;

import javafx.scene.image.Image;
import main.game.GameBoard;
import main.game.effects.Effect;
import main.game.player.Player;

/**
 * Created by IntelliJ IDEA.
 * User: felcamag
 * Date: 3. 6. 2020
 * Time: 14:41
 */
public abstract class Buff extends Effect {

    /**
     * Constructor of Buff.
     * @param img The buff's image.
     */
    public Buff(Image img) {
        super(img);
    }

    @Override
    public boolean isAbleToPickUp() {
        return true;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean hurtsPlayer() {
        return false;
    }

    @Override
    public int getOwnerNumber() {
        return -1;
    }

    /**
     * Activates the buff.
     * @param player The player who picked up the buff.
     * @param gameBoard The given game board.
     */
    public abstract void activate(Player player, GameBoard gameBoard);
}
