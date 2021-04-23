package main.game.effects;

import javafx.scene.image.Image;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 5. 5. 2020
 * Time: 18:39
 */
public abstract class Effect{
    private Image img;

    /**
     * Constructor for Effect.
     * @param img The Effect's image.
     */
    public Effect(Image img) {
        this.img = img;
    }

    /**
     * Asks if the effect is able to pick up by player.
     * @return TRUE if possible, FALSE if not possible
     */
    public abstract boolean isAbleToPickUp();

    /**
     * Asks if the effect is passable by player.
     * @return TRUE if possible, FALSE if not possible
     */
    public abstract boolean isPassable();

    /**
     * Gets the Effect's image.
     * @return The Effect's image.
     */
    public Image getImage() {
        return img;
    }

    /**
     * Sets the Effect's image.
     * @param img The Effect's image.
     */
    void setImg(Image img) {
        this.img = img;
    }

    /**
     * Makes a copy of the effect.
     * @return The copy.
     */
    public abstract Effect copyEffect();

    /**
     * Copies effects to a two dimensional array.
     * @param effects Effects on the game board.
     * @return Two dimensional array of effects.
     */
    public static Effect[][] copyEffects(Effect[][] effects) {
        Effect[][] res = new Effect[effects.length][effects[0].length];
        for (int i = 0; i < effects.length; i++) {
            for (int j = 0; j < effects.length; j++) {
                res[i][j] = effects[i][j].copyEffect();
            }
        }
        return res;
    }

    /**
     * Asks if the effect hurts player.
     * @return TRUE if it hurts, FALSE if not
     */
    public abstract boolean hurtsPlayer();

    /**
     * Asks for the player's number that placed the effect.
     * @return The player's number.
     */
    public abstract int getOwnerNumber();

}
