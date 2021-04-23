package main.game.blocks;

import main.images.ImageLoader;
import main.images.ImagePaths;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 27. 3. 2020
 * Time: 19:26
 */
public class IndestructibleWall extends Wall {
    public IndestructibleWall() {
        super(false, ImageLoader.loadImage(ImagePaths.INDESTRUCTIBLE_WALL));
    }

    @Override
    public Block copy() {
        return new IndestructibleWall();
    }
}
