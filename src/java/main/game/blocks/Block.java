package main.game.blocks;


import javafx.scene.image.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 3. 2020
 * Time: 13:57
 */
public abstract class Block {
    private Image img;

    /**
     * Constructor for Block.
     * @param img The image for our Block.
     */
    protected Block(Image img) {
        this.img = img;
    }

    /**
     * Asks if the block can be passed by player.
     * @return TRUE is passable, FALSE if not passable
     */
    public abstract boolean isPassable();

    /**
     * Asks if the block is destructible.
     * @return TRUE if destructible, FALSE if not destructible
     */
    public abstract boolean isDestructible();

    /**
     * Sets the image to given image.
     * @param img The given image.
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Gets the image of the Block.
     * @return The image of the Block.
     */
    public Image getImg() {
        return img;
    }

    /**
     * Makes a copy of the Block.
     * @return The copy of our Block.
     */
    public abstract Block copy();
}
