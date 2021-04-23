package main.game.effects;

import main.images.ImagePaths;

import static main.images.ImagePaths.FLAMES;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 5. 5. 2020
 * Time: 17:20
 */
public class Explosion extends Effect {
    private int phaseIndex;
    private ImagePaths.FlameType type;
    private int ownerNumber;

    /**
     * The Constructor of Explosion
     * @param type The Explosion's type.
     * @param phaseIndex The Explosion's phase index.
     * @param ownerNumber The Explosion's owner number.
     */
    Explosion(ImagePaths.FlameType type, int phaseIndex, int ownerNumber) {
        super(FLAMES.get(type).get(phaseIndex));
        this.type = type;
        this.phaseIndex = phaseIndex;
        this.ownerNumber = ownerNumber;
    }

    @Override
    public boolean isAbleToPickUp() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    private int getPhaseIndex() {
        return phaseIndex;
    }

    @Override
    public int getOwnerNumber() {
        return ownerNumber;
    }

    private ImagePaths.FlameType getType() {
        return type;
    }

    @Override
    public Effect copyEffect() {
        return new Explosion(getType(), getPhaseIndex(), getOwnerNumber());
    }

    @Override
    public boolean hurtsPlayer() {
        return true;
    }

    /**
     * Changes explosion phase.
     * @param phaseIndex Phase of explosion.
     */
    void changeExplosion(int phaseIndex) {
        if (phaseIndex >= 0 && phaseIndex < 6) {
            setImg(FLAMES.get(getType()).get(phaseIndex));
            this.phaseIndex = phaseIndex;
        }
    }


}
