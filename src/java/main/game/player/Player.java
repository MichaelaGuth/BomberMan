package main.game.player;

import javafx.scene.image.Image;
import main.Constants.Direction;
import main.game.Game;
import main.game.GameBoard;
import main.game.effects.Bomb;
import main.game.effects.Effect;
import main.game.effects.Effects;
import main.game.effects.buffs.Buff;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.Constants.*;
import static main.game.GameBoard.*;
import static main.game.GameUtils.checkCoordinatesAreInGameBoard;
import static main.game.player.Player.PlayerStartPosition.*;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 3. 2020
 * Time: 13:38
 */
public class Player {
    private int score;
    private int positionX;
    private int positionY;
    private Image img;
    private int lives;
    private GameCharacter character;
    private String username;
    private int bombFlameLength;
    private boolean immune;
    private int number;

    private static Logger LOGGER = Logger.getLogger(Player.class.getName());

    public enum PlayerStartPosition {
        PLAYER_1(LEFT_BORDER, TOP_BORDER), PLAYER_2(RIGHT_BORDER, TOP_BORDER), PLAYER_3(LEFT_BORDER, BOTTOM_BORDER), PLAYER_4(RIGHT_BORDER, BOTTOM_BORDER);

        private int x, y;

        PlayerStartPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }
    }

    // CONSTRUCTOR

    /**
     * The Constructor of Player.
     * @param character The Player's game character.
     * @param username The Player's username.
     * @param number The Player's number.
     */
    public Player(GameCharacter character, String username, int number) {
        this.score = 0;
        this.character = character;
        this.username = username;
        this.number = number;

        setStartParameters();

        LOGGER.log(Level.INFO, "Created player named: "+ getUsername() + " with number: " + getNumber());
    }


    // SETTERS:

    /**
     * Sets the Player's image to given image.
     * @param img The given image.
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Sets the Player's immunity to given immunity.
     * @param immune The given immunity.
     */
    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    /**
     * Sets the Player's position X to given position X.
     * @param positionX The given position X.
     */
    private void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Sets the Player's position Y to given position Y.
     * @param positionY The given position Y.
     */
    private void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Sets the Player's lives to given lives count.
     * @param lives The given lives count.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Sets the Player's bomb flame length to given flame length.
     * @param flameLength The given flame length.
     */
    private void setFlameLength(int flameLength) {
        this.bombFlameLength = flameLength;
    }

    /**
     * Sets player's starting position.
     */
    private void setStartPosition() {
        switch (getNumber()) {
            case 1:
                setPositionX(PLAYER_1.getX());
                setPositionY(PLAYER_1.getY());
                break;
            case 2:
                setPositionX(PLAYER_2.getX());
                setPositionY(PLAYER_2.getY());
                break;
            case 3:
                setPositionX(PLAYER_3.getX());
                setPositionY(PLAYER_3.getY());
                break;
            case 4:
                setPositionX(PLAYER_4.getX());
                setPositionY(PLAYER_4.getY());
                break;
        }
    }

    /**
     * Sets the Player's score to the given score value.
     * @param score The given score value.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets player's parameters for the start.
     */
    public void setStartParameters() {
        setFlameLength(MIN_FLAME_LENGTH);
        setLives(LIFE_COUNT);
        setStartPosition();
        setImmune(false);
        getCharacter().setCurrPose(PlayerPose.LOOK_DOWN);
        setImg(character.getCurrPoseImage());
    }


    // GETTERS:

    /**
     * Gets the Player's image.
     * @return The Player's image.
     */
    public Image getImage() {
        return img;
    }

    /**
     * Gets the Player's bomb.
     * @return The Player's bomb.
     */
    private Bomb getBomb() {
        return new Bomb(bombFlameLength, getPositionX(), getPositionY(), getNumber());
    }

    /**
     * Gets the Player's character.
     * @return The Player's character.
     */
    public GameCharacter getCharacter() {
        return character;
    }

    /**
     * Gets the Player's score.
     * @return The Player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the Player's username.
     * @return The Player's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the Player's number.
     * @return The Player's number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the Player's lives count.
     * @return The Player's lives count.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Gets the Player's position X.
     * @return The Player's position X.
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Gets the Player's position Y.
     * @return The Player's position Y.
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Gets the Player's bomb flame length.
     * @return The Player's bomb flame length.
     */
    int getFlameLength() {
        return bombFlameLength;
    }


    // PROPERTIES:

    /**
     * Checks if the player is immune.
     * @return TRUE is the player is immune, FALSE if not
     */
    public boolean isImmune() {
        return immune;
    }

    /**
     * Checks if the Player is alive.
     * @return TRUE if alive, FALSE if not
     */
    public boolean isAlive() {
        if (lives > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the Player is AI or Real Player
     * @return TRUE if AI
     * FALSE if Real Player
     */
    public boolean isAI() {
        return false;
    }

    /**
     * Removes one life from the player if he doesn't have the minimum of lives already.
     */
    public void minusOneLife() {
        if (getLives() > 0) {
            setLives(getLives() - 1);
        }
    }

    /**
     * Adds one life to a player if he doesn't have maximum number of lives already.
     */
    public void plusOneLife() {
        if (getLives() < 5) {
            setLives(getLives() + 1);
        }
    }

    /**
     * Adds given points to current score.
     * @param addition Number of points received.
     */
    public void addScore(int addition) {
        this.score += addition;
    }

    /**
     * Increases the length of explosion flames.
     */
    public void increaseFlameLength() {
        if (bombFlameLength < MAX_FLAME_LENGTH) {
            bombFlameLength++;
        }
    }

    /**
     * Decreases the length of explosion flames.
     */
    public void decreaseFlameLength() {
        if (bombFlameLength > MIN_FLAME_LENGTH) {
            bombFlameLength--;
        }
    }


    // ACTIONS:

    /**
     * Moves the Player in given direction on the given game board.
     * @param dir The given Direction enum value
     * @param game The given GameBoard
     * @return TRUE if moving was possible
     *         FALSE if not
     */
    public boolean move(Direction dir, Game game) {

        boolean hasMoved = false;

        int tmpX = this.getPositionX() + dir.getX();
        int tmpY = this.getPositionY() + dir.getY();

        if (checkCoordinatesAreInGameBoard(tmpX, tmpY)) {
            if (game.getBlocks()[tmpX][tmpY].isPassable()) {
                this.setPositionX(tmpX);
                this.setPositionY(tmpY);
                hasMoved = true;
                setScore(getScore() + 1);
                LOGGER.log(Level.INFO, "Player named " + getUsername() + " changed position to [" + getPositionX() + "," + getPositionY() + "].");
            }
        }

        updatePlayerImmunity(game.getEffects(), game.getAlivePlayers());

        switch (dir) {
            case RIGHT:
                if (immune) {
                    character.setCurrPose(PlayerPose.LOOK_RIGHT_IMMUNE);
                } else {
                    character.setCurrPose(PlayerPose.LOOK_RIGHT);
                }
                break;
            case LEFT:
                if (immune) {
                    character.setCurrPose(PlayerPose.LOOK_LEFT_IMMUNE);
                } else {
                    character.setCurrPose(PlayerPose.LOOK_LEFT);
                }
                break;
            case DOWN:
                if (immune) {
                    character.setCurrPose(PlayerPose.LOOK_DOWN_IMMUNE);
                } else {
                    character.setCurrPose(PlayerPose.LOOK_DOWN);
                }
                break;
            case UP:
                if (immune) {
                    character.setCurrPose(PlayerPose.LOOK_UP_IMMUNE);
                } else {
                    character.setCurrPose(PlayerPose.LOOK_UP);
                }
                break;
        }

        setImg(character.getCurrPoseImage());

        return hasMoved;
    }

    /**
     * Placing a bomb and starting the timer for the explosion.
     * @param game The current game.
     */
    public void placeBomb(Game game) {
        Bomb bomb = getBomb();
        Player owner = this;

        game.getEffects()[getPositionX()][getPositionY()].add(bomb);
        bomb.setActive(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                owner.addScore(SCORE_BOMB_PLACED);
                bomb.explode(game, owner);
            }
        }, bomb.getTimeToExplosion() * 1000);

        LOGGER.log(Level.INFO, "Player named " + getUsername() + " placed a bomb on coordinates [" + getPositionX() + "," + getPositionY() + "].");
    }

    /**
     * When effect hurts a player, player loses one life. Players score is updated. Immunity is set for one second after player has been hit.
     * @param effects Effects on the game board.
     * @param alivePlayers List of alive players.
     */
    public void updatePlayerImmunity(Effects[][] effects, List<Player> alivePlayers) {
        if (!effects[getPositionX()][getPositionY()].isEmpty()) {
            for (Effect e :
                    effects[getPositionX()][getPositionY()].getList()) {
                if (e.hurtsPlayer()) {
                    if (!isImmune()) {
                        minusOneLife();
                        for (Player possibleBombOwner : alivePlayers) {
                            if (possibleBombOwner.getNumber() == e.getOwnerNumber()) {
                                if (possibleBombOwner.getNumber() == getNumber()) {
                                    possibleBombOwner.addScore(SCORE_HIT_BY_MYSELF);
                                    LOGGER.log(Level.INFO, "Player named "+ getUsername() + " got hit by himself. " + SCORE_HIT_BY_MYSELF + " score points");
                                } else {
                                    possibleBombOwner.addScore(SCORE_ENEMY_HIT);
                                    LOGGER.log(Level.INFO, "Player named "+ possibleBombOwner.getUsername() + " managed to hit player named " + getUsername() + ". +" + SCORE_ENEMY_HIT + " score points");
                                    addScore(SCORE_HIT_BY_ENEMY);
                                    LOGGER.log(Level.INFO, "Player named "+ getUsername() + " got hit by player named " + possibleBombOwner.getUsername() + ". " + SCORE_HIT_BY_ENEMY + " score points");
                                }
                            }
                        }
                        setImmune(true);

                        Timer immunityTimer = new Timer();
                        immunityTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setImmune(false);
                            }
                        }, 1000);
                    }
                }
            }
        }
    }

    /**
     * Player picks up a buff.
     * @param gameBoard The game board.
     */
    public void pickBuff(GameBoard gameBoard) {
        Buff b = gameBoard.getEffects()[getPositionX()][getPositionY()].getBuff();
        if (b != null) {
            b.activate(this, gameBoard);
            LOGGER.log(Level.INFO, "Player named " + getUsername() + " picked up a " + b.getClass().getName() + ".");
        }
    }
}
