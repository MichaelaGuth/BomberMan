package main.game.blocks;

import main.images.ImageLoader;
import main.images.ImagePaths;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 27. 3. 2020
 * Time: 19:22
 */
public class Crate extends Wall {
    public Crate() {
        super(true, ImageLoader.loadImage(ImagePaths.CRATE));
    }

    @Override
    public Block copy() {
        return new Crate();
    }
}
