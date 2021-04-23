package main.game.blocks;

import javafx.scene.image.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 3. 2020
 * Time: 13:48
 */
public abstract class Wall extends Block {
    private boolean destructible;

    protected Wall(boolean destructible, Image img) {
        super(img);
        this.destructible = destructible;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    public boolean isDestructible() {
        return destructible;
    }
}
