package main.game.effects;

import main.game.Game;
import main.game.GameBoard;
import main.game.player.Player;
import main.images.ImageLoader;

import java.util.*;

import static main.Constants.*;
import static main.game.GameUtils.checkCoordinatesAreInGameBoard;
import static main.images.ImagePaths.*;


/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 3. 2020
 * Time: 13:37
 */
public class Bomb extends Effect {
    private int flameLength;
    private int timeToExplosion;
    private boolean active;

    private int ownerNumber;

    private int posX;
    private int posY;
    
    private LinkedList<Explosion> explosions;

    private static int phaseIndex;

    private Timer timer;


    /**
     * The constructor of Bomb.
     * @param flameLength The Bomb's flame length.
     * @param timeToExplosion The Bomb's time to explosion.
     * @param posX The Bomb's position X.
     * @param posY The Bomb's position Y.
     * @param ownerNumber The Bomb's owner number. (The number of player that placed it.)
     */
    private Bomb(int flameLength, int timeToExplosion, int posX, int posY, int ownerNumber) {
        super(ImageLoader.loadImage(BOMB));
        this.posX = posX;
        this.posY = posY;
        this.flameLength = flameLength;
        this.timeToExplosion = timeToExplosion;
        this.ownerNumber = ownerNumber;
        active = false;
        
        explosions = new LinkedList<>();
        explosions.add(new Explosion(FlameType.FLAME_START, 0, ownerNumber));            // 0
        explosions.add(new Explosion(FlameType.FLAME_HORIZONTAL_MID, 0, ownerNumber));   // 1
        explosions.add(new Explosion(FlameType.FLAME_VERTICAL_MID, 0, ownerNumber));     // 2
        explosions.add(new Explosion(FlameType.FLAME_TOP_END, 0, ownerNumber));          // 3
        explosions.add(new Explosion(FlameType.FLAME_RIGHT_END, 0, ownerNumber));        // 4
        explosions.add(new Explosion(FlameType.FLAME_BOT_END, 0, ownerNumber));          // 5
        explosions.add(new Explosion(FlameType.FLAME_LEFT_END, 0, ownerNumber));         // 6
    }

    /**
     * The constructor of Bomb.
     * @param flameLength The Bomb's flame length.
     * @param posX The Bomb's position X.
     * @param posY The Bomb's position Y.
     * @param ownerNumber The Bomb's owner number. (The number of player that placed it.)
     */
    public Bomb(int flameLength, int posX, int posY, int ownerNumber) {
        this(flameLength, TIME_TILL_EXPLOSION, posX, posY, ownerNumber);
    }

    /**
     * Gets the Bomb's position X.
     * @return The Bomb's position X.
     */
    private int getPosX() {
        return posX;
    }

    /**
     * Gets the Bomb's position Y.
     * @return The Bomb's position Y.
     */
    private int getPosY() {
        return posY;
    }

    /**
     * Gets the Bomb's list of explosions.
     * @return The Bomb's list of explosions.
     */
    private LinkedList<Explosion> getExplosions() {
        return explosions;
    }

    /**
     * Creates the bomb explosion.
     * @param game The given game.
     * @param owner Player who placed the bomb.
     */
    public void explode(Game game, Player owner) {
        phaseIndex = 1;
        GameBoard gameBoard = game.getGameBoard();
        gameBoard.getEffects()[getPosX()][getPosY()].remove(this);
        
        LinkedList<int[]> destroyedBlocks = setFlames(gameBoard);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (phaseIndex == 6) {
                    for (Effect e :
                            getExplosions()) {
                        gameBoard.removeEffect(e);
                    }

                    for (int[] coords :
                            destroyedBlocks) {
                        gameBoard.destroyBlock(coords[0], coords[1]);
                        owner.addScore(SCORE_BLOCK_DESTROYED);
                    }

                    timer.cancel();
                    timer.purge();
                } else {
                    for (Explosion e :
                            explosions) {
                        e.changeExplosion(phaseIndex);
                    }
                    phaseIndex++;
                }
            }
        }, 100, 100);

    }

    /**
     * Creates the animation of explosion.
     * @param gameBoard The given game board.
     * @return Blocks destroyed by explosion.
     */
    private LinkedList<int[]> setFlames(GameBoard gameBoard) {
        int tmpX, tmpY;
        LinkedList<int[]> destroyedBlocks = new LinkedList<>();

        gameBoard.addEffect(explosions.get(FLAME_INDEX_START), getPosX(), getPosY());

        // RIGHT
        for (int i = 1; i <= getFlameLength(); i++) {
            tmpX = getPosX() + i;
            tmpY = getPosY();
            
            if (checkCoordinatesAreInGameBoard(tmpX, tmpY)) {
                if (gameBoard.getBlocks()[tmpX][tmpY].isPassable()) {
                    if (i == getFlameLength()) {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_RIGHT), tmpX, tmpY);
                    } else {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_MID_HORIZONTAL), tmpX, tmpY);
                    }
                } else if (gameBoard.getBlocks()[tmpX][tmpY].isDestructible()) {
                    gameBoard.addEffect(explosions.get(FLAME_INDEX_RIGHT), tmpX, tmpY);
                    destroyedBlocks.add(new int[] { tmpX, tmpY });
                    break;
                } else {
                    break;
                }
            }
        }

        // LEFT
        for (int i = 1; i <= getFlameLength(); i++) {
            tmpX = getPosX() - i;
            tmpY = getPosY();

            if (checkCoordinatesAreInGameBoard(tmpX, tmpY)) {
                if (gameBoard.getBlocks()[tmpX][tmpY].isPassable()) {
                    if (i == getFlameLength()) {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_LEFT), tmpX, tmpY);
                    } else {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_MID_HORIZONTAL), tmpX, tmpY);
                    }
                } else if (gameBoard.getBlocks()[tmpX][tmpY].isDestructible()) {
                    gameBoard.addEffect(explosions.get(FLAME_INDEX_LEFT), tmpX, tmpY);
                    destroyedBlocks.add(new int[] { tmpX, tmpY });
                    break;
                } else {
                    break;
                }
            }
        }
        
        // TOP
        for (int i = 1; i <= getFlameLength(); i++) {
            tmpX = getPosX();
            tmpY = getPosY() - i;

            if (checkCoordinatesAreInGameBoard(tmpX, tmpY)) {
                if (gameBoard.getBlocks()[tmpX][tmpY].isPassable()) {
                    if (i == getFlameLength()) {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_TOP), tmpX, tmpY);
                    } else {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_MID_VERTICAL), tmpX, tmpY);
                    }
                } else if (gameBoard.getBlocks()[tmpX][tmpY].isDestructible()) {
                    gameBoard.addEffect(explosions.get(FLAME_INDEX_TOP), tmpX, tmpY);
                    destroyedBlocks.add(new int[] { tmpX, tmpY });
                    break;
                } else {
                    break;
                }
            }
        }
        
        // BOT
        for (int i = 1; i <= getFlameLength(); i++) {
            tmpX = getPosX();
            tmpY = getPosY() + i;

            if (checkCoordinatesAreInGameBoard(tmpX, tmpY)) {
                if (gameBoard.getBlocks()[tmpX][tmpY].isPassable()) {
                    if (i == getFlameLength()) {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_BOT), tmpX, tmpY);
                    } else {
                        gameBoard.addEffect(explosions.get(FLAME_INDEX_MID_VERTICAL), tmpX, tmpY);
                    }
                } else if (gameBoard.getBlocks()[tmpX][tmpY].isDestructible()) {
                    gameBoard.addEffect(explosions.get(FLAME_INDEX_BOT), tmpX, tmpY);
                    destroyedBlocks.add(new int[] { tmpX, tmpY });
                    break;
                } else {
                    break;
                }
            }
        }
        
        return destroyedBlocks;
    }

    @Override
    public boolean isAbleToPickUp() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public Effect copyEffect() {
        return new Bomb(this.getFlameLength(), this.getTimeToExplosion(), this.getPosX(), this.getPosY());
    }

    @Override
    public boolean hurtsPlayer() {
        return false;
    }

    @Override
    public int getOwnerNumber() {
        return ownerNumber;
    }


    /**
     * Sets the bomb active.
     * @param active true/false
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the Bomb's flame length.
     * @return The Bomb's flame length.
     */
    private int getFlameLength() {
        return flameLength;
    }

    /**
     * Gets the Bomb's time to explosion.
     * @return The Bomb's time to explosion.
     */
    public int getTimeToExplosion() {
        return timeToExplosion;
    }

    /**
     * Asks if the Bomb is active.
     * @return TRUE if yes, FALSE if no.
     */
    public boolean isActive() {
        return active;
    }
}


