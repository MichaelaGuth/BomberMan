package main.game.effects;

import main.images.ImageLoader;

import static main.images.ImagePaths.PORTAL;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 2. 4. 2020
 * Time: 22:22
 */
public class Portal extends Effect {
    private int newPosX, newPosY;

    public Portal(int newPosX, int newPosY) {
        super(ImageLoader.loadImage(PORTAL));
        this.newPosX = newPosX;
        this.newPosY = newPosY;
    }

    @Override
    public boolean isAbleToPickUp() {
        return false;
    }

    public boolean isPassable() {
        return true;
    }

    @Override
    public Effect copyEffect() {
        return new Portal(getNewPosX(), getNewPosY());
    }

    @Override
    public boolean hurtsPlayer() {
        return false;
    }

    @Override
    public int getOwnerNumber() {
        return -1;
    }

    public int getNewPosX() {
        return newPosX;
    }

    public int getNewPosY() {
        return newPosY;
    }
}
