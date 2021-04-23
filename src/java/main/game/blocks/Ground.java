package main.game.blocks;

import main.images.ImageLoader;
import main.images.ImagePaths;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 3. 2020
 * Time: 14:11
 */
public class Ground extends Block {

    public Ground() {
        super(ImageLoader.loadImage(ImagePaths.GROUND));
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean isDestructible() {
        return false;
    }

    @Override
    public Block copy() {
        return new Ground();
    }
}
