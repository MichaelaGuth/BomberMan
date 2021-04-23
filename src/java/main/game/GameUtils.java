package main.game;

import javafx.scene.canvas.GraphicsContext;
import main.Constants;
import main.game.blocks.Ground;
import main.game.effects.Effect;
import main.game.effects.Effects;
import main.game.player.Player;
import main.game.player.PlayerPose;

import java.util.List;

import static main.Constants.BLOCK_SIZE;
import static main.game.GameBoard.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 5. 5. 2020
 * Time: 17:35
 */
public class GameUtils {

    /**
     * Checks if the given coordinates are in game board.
     * @param tmpX The given coordinate X.
     * @param tmpY The given coordinate Y.
     * @return TRUE if they are, FALSE if not.
     */
    public static boolean checkCoordinatesAreInGameBoard(int tmpX, int tmpY) {
        if (tmpX <= RIGHT_BORDER && tmpX >= LEFT_BORDER) {
            return tmpY >= TOP_BORDER && tmpY <= BOTTOM_BORDER;
        }
        return false;
    }

    /**
     * Checks if the given coordinates are passable in game board.
     * @param tmpX The given coordinate X.
     * @param tmpY The given coordinate Y.
     * @param gameboard The given game board.
     * @return TRUE if they are, FALSE if not.
     */
    public static boolean checkCoordinatesArePassable(int tmpX, int tmpY, GameBoard gameboard) {
        boolean effectsArePassable = true;
        Effects[][] effects = gameboard.getEffects();

        for (Effect e : effects[tmpX][tmpY].getList()) {
            effectsArePassable &= e.isPassable();
        }

        return (gameboard.getBlocks()[tmpX][tmpY].isPassable() && effectsArePassable);
    }

    // DRAW FUNCTIONS:
    /**
     * Draws the given gameBoard on our canvas gamePlan.
     * @param game The given GameBoard.
     * @param gc The GraphicsContext for gamePlan Canvas.
     */
    static void drawGame(Game game, GraphicsContext gc) {
        drawGameBoard(game.getGameBoard(), gc);
        updatePlayersImages(game);
        drawPlayers(game.getAlivePlayers(), gc);
    }

    /**
     * Draws the game board on the canvas.
     * @param gameBoard The game board.
     * @param gc The GraphicsContext for gamePlan Canvas.
     */
    private static void drawGameBoard(GameBoard gameBoard, GraphicsContext gc) {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                gc.drawImage(
                        new Ground().getImg(),
                        i* Constants.BLOCK_SIZE,
                        j*Constants.BLOCK_SIZE
                );
                gc.drawImage(
                        gameBoard.getBlocks()[i][j].getImg(),
                        i* Constants.BLOCK_SIZE,
                        j*Constants.BLOCK_SIZE
                );
            }
        }
        drawEffects(gameBoard.getEffects(), gc);
    }

    /**
     * Draws the effects on the canvas.
     * @param effects The given effects.
     * @param gc The GraphicsContext for gamePlan Canvas.
     */
    private static void drawEffects(Effects[][] effects, GraphicsContext gc) {
        for (int i = 0; i < effects.length; i++) {
            for (int j = 0; j < effects.length; j++) {
                if (!effects[i][j].isEmpty()) {
                    for (Effect effect : effects[i][j].getList()) {
                        gc.drawImage(
                                effect.getImage(),
                                i* Constants.BLOCK_SIZE,
                                j*Constants.BLOCK_SIZE
                        );
                    }
                }
            }
        }
    }

    /**
     * Draws the given ArrayList of Players on our Canvas gamePlan.
     * @param players The given ArrayList of Players.
     * @param gc The GraphicsContext for gamePlan Canvas.
     */
    private static void drawPlayers(List<Player> players, GraphicsContext gc) {
        for (Player player : players) {
            drawPlayer(player, gc);
        }
    }

    /**
     * Draws the given Player on our Canvas gamePlan.
     * @param player The given Player.
     * @param gc The GraphicsContext for gamePlan Canvas.
     */
    private static void drawPlayer(Player player, GraphicsContext gc) {
        if (player.isAlive()) {
            gc.drawImage(player.getImage(), player.getPositionX()*BLOCK_SIZE, player.getPositionY()*BLOCK_SIZE);
        }
    }

    /**
     * Updates the players images based on game.
     * @param game The game.
     */
    private static void updatePlayersImages(Game game) {
        for (Player p :
                game.getAlivePlayers()) {
            updatePlayerImage(p);
        }
    }

    /**
     * Updates the player's image.
     * @param player The given player.
     */
    private static void updatePlayerImage(Player player) {
        if (player.isImmune()) {
            switch (player.getCharacter().getCurrPose()) {
                case LOOK_DOWN:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_DOWN_IMMUNE);
                    break;
                case LOOK_LEFT:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_LEFT_IMMUNE);
                    break;
                case LOOK_UP:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_UP_IMMUNE);
                    break;
                case LOOK_RIGHT:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_RIGHT_IMMUNE);
                    break;
                default:
                    // NOP
            }
        } else {
            switch (player.getCharacter().getCurrPose()) {
                case LOOK_UP_IMMUNE:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_UP);
                    break;
                case LOOK_DOWN_IMMUNE:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_DOWN);
                    break;
                case LOOK_LEFT_IMMUNE:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_LEFT);
                    break;
                case LOOK_RIGHT_IMMUNE:
                    player.getCharacter().setCurrPose(PlayerPose.LOOK_RIGHT);
                    break;
                default:
                    // NOP
            }
        }

        player.setImg(player.getCharacter().getCurrPoseImage());
    }

}
