package main.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.Constants;
import main.Main;
import main.dialogs.Dialog;
import main.game.player.Player;
import main.images.ImageLoader;
import main.images.ImagePaths;
import javafx.event.EventHandler;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.Constants.FXML_MENU;
import static main.Constants.SCORE_PER_TIMER;
import static main.Utils.changeStage;
import static main.dialogs.Dialog.raiseDialog;
import static main.game.GameUtils.drawGame;
import static main.images.ImagePaths.*;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 3. 2020
 * Time: 12:21
 */
public class GameController implements EventHandler<KeyEvent> {

    public Canvas gamePlan;

    public ImageView infoBoard;

    public ImageView backButton;
    public ImageView soundButton;

    public ImageView player1Picture;
    public ImageView player1Stats;

    public ImageView player2Picture;
    public ImageView player2Stats;

    public ImageView player3Picture;
    public ImageView player3Stats;

    public ImageView player4Picture;
    public ImageView player4Stats;

    public Label Player4ScoreLabel;
    public Label Player3ScoreLabel;
    public Label Player2ScoreLabel;
    public Label Player1ScoreLabel;

    public Label Player3Label;
    public Label Player4Label;

    public static volatile Game game;

    private boolean soundOn;
    private boolean intro;
    private MediaPlayer introPlayer;
    private MediaPlayer loopPlayer;

    private PlayerObjects PLAYER_OBJECTS;

    private class PlayerObjects {
        private ArrayList<HashMap<String, ImageView>> map;

        private final String STATS = "sts";
        private final String PICTURE = "pic";

        private PlayerObjects() {
            this.map = new ArrayList<>();
            HashMap<String, ImageView> tmp;

            tmp = new HashMap<>();
            tmp.put(STATS, player1Stats);
            tmp.put(PICTURE, player1Picture);
            this.map.add(tmp);

            tmp = new HashMap<>();
            tmp.put(STATS, player2Stats);
            tmp.put(PICTURE, player2Picture);
            this.map.add(tmp);

            tmp = new HashMap<>();
            tmp.put(STATS, player3Stats);
            tmp.put(PICTURE, player3Picture);
            this.map.add(tmp);

            tmp = new HashMap<>();
            tmp.put(STATS, player4Stats);
            tmp.put(PICTURE, player4Picture);
            this.map.add(tmp);
        }

        private ImageView getPlayerStats(int index) {
            return map.get(index).get(STATS);
        }

        private ImageView getPlayerPic(int index) {
            return map.get(index).get(PICTURE);
        }
    }

    private GraphicsContext gc;
    private AnimationTimer animationTimer;
    private boolean stopScore = false;
    private boolean stopGame = false;
    private boolean sleepGame = false;
    private boolean newGame = false;
    private boolean isGameOver = false;
    private static Logger LOGGER = Logger.getLogger(GameController.class.getName());

    @FXML
    public void initialize() {
        LOGGER.log(Level.INFO, "Setting up game..");

        gc = gamePlan.getGraphicsContext2D();
        PLAYER_OBJECTS = new PlayerObjects();

        setImages();
        setGame();
        Main.stage.addEventHandler(KeyEvent.KEY_PRESSED, this);

        // set sound
        setSoundPlayers();
        turnSoundOn();

        Thread gameThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!stopGame) {
                    if (!sleepGame && game.checkIfGameOver()) {
                        if (game.getNumOfRounds() == 1) {
                            isGameOver = true;
                        } else {
                            newGame = true;
                        }
                    }

                    game.update();

                    for (Player p : game.getAlivePlayers()) {
                        p.updatePlayerImmunity(game.getGameBoard().getEffects(), game.getAlivePlayers());
                        p.pickBuff(game.getGameBoard());
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.WARNING, e.getMessage());
                    }
                }
            }
        });

        // sets the animation timer.
        LOGGER.log(Level.INFO, "Setting animation timer...");
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //LOGGER.log(Level.INFO, "Checking game over...");
                if(isGameOver) {
                    animationTimer.stop();
                    gameOver();
                } else if (newGame) {
                    newGame = false;
                    sleepGame = true;
                    animationTimer.stop();
                    game = new Game(new GameBoard(), game.getNumOfRounds() - 1, game.getNumOfPlayers(), game.getNumOfAIs(), game.getAllPlayers());
                    game.setStartParameters();
                    animationTimer.start();
                    sleepGame = false;
                }

                //LOGGER.log(Level.INFO, "Updating players..");
                for (Player p : game.getAlivePlayers()) {
                    updatePlayerLivesCount(p);
                    updatePlayerScore(p);
                }


                //LOGGER.log(Level.INFO, "Drawing game..");
                drawGame(game, gc);
            }
        };
        LOGGER.log(Level.INFO, "Animation timer has been set.");

        Thread scoreThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stopScore) {

                    for (Player player : game.getAlivePlayers()) {
                        player.setScore(player.getScore() + SCORE_PER_TIMER);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.WARNING, e.getMessage());
                    }
                }
            }
        });

        LOGGER.log(Level.INFO, "Score timer set.");

        LOGGER.log(Level.INFO, "Starting animation...");
        animationTimer.start();
        gameThread.setDaemon(true);
        gameThread.start();
        scoreThread.setDaemon(true);
        scoreThread.start();
    }


    // SET FUNCTIONS

    /**
     * Creates the components that manage sound.
     */
    private void setSoundPlayers() {
        introPlayer = new MediaPlayer(new Media(GameController.class.getResource("/music/That's it for today INTRO.wav").toString()));
        introPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                introPlayer.stop();
                intro = false;
                loopPlayer.play();
            }
        });

        loopPlayer = new MediaPlayer(new Media(GameController.class.getResource("/music/That's it for today (LOOP).wav").toString()));
        loopPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                loopPlayer.seek(Duration.ZERO);
            }
        });

        LOGGER.log(Level.INFO, "Music Players set.");
    }

    /**
     * Sets images to our components. (Buttons)
     */
    private void setImages() {
        infoBoard.setImage(ImageLoader.loadImage(LEFT_INFO_PANEL));
        backButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
        soundButton.setImage(ImageLoader.loadImage(SOUND_BUTTON_ON));
        LOGGER.log(Level.INFO, "Images has been set.");
    }

    /**
     * Sets the base Game parameters.
     */
    private void setGame() {
        setPlayers(game.getAlivePlayers());

        switch (game.getNumOfPlayers()) {
            case 2:
                Player3ScoreLabel.setVisible(false);
                Player3Label.setVisible(false);
            case 3:
                Player4ScoreLabel.setVisible(false);
                Player4Label.setVisible(false);
            default:
                // NOP
        }
    }

    /**
     * Sets the PlayerObjects by the given ArrayList of Players.
     *
     * @param players The given ArrayList of Players.
     */
    private void setPlayers(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            setPlayer(i, players.get(i));
        }

        for (int i = 3; i > players.size() - 1; i--) {
            setNoPlayer(i);
        }
    }

    /**
     * Sets the PlayerObjects of given index to transparent since no Player is assigned.
     * @param index The index of Player objects to be set invisible.
     */
    private void setNoPlayer(int index) {
        PLAYER_OBJECTS.getPlayerPic(index).setVisible(false);
        PLAYER_OBJECTS.getPlayerStats(index).setVisible(false);
    }

    /**
     * Sets the PlayerObjects of given index by the given Player.
     *
     * @param index  The index of PlayerObjects
     * @param player The given Player.
     */
    private void setPlayer(int index, Player player) {
        PLAYER_OBJECTS.getPlayerPic(index).setImage(player.getCharacter().getProfilePic(true));
        PLAYER_OBJECTS.getPlayerStats(index).setImage(ImageLoader.loadImage(FIVE_LIVES));
    }


// LISTENERS:

    // BUTTON CLICKS:

    /**
     * Sets the sound Off/On depending on the current state of sound.
     * @param mouseEvent not used
     */
    public void soundButtonClick(MouseEvent mouseEvent) {
        if (soundOn) {
            turnSoundOff();
            soundButton.setImage(ImageLoader.loadImage(SOUND_BUTTON_OFF_CLICK));
        } else {
            turnSoundOn();
            soundButton.setImage(ImageLoader.loadImage(SOUND_BUTTON_ON_CLICK));
        }
    }

    /**
     * Returns user back menu scene and resets the game parameters. Saves score.
     * @param mouseEvent not used
     */
    public void backButtonClick(MouseEvent mouseEvent) {
        if (soundOn) {
            turnSoundOff();
        }

        loopPlayer = null;
        introPlayer = null;

        changeStage(FXML_MENU);

        animationTimer.stop();
        animationTimer = null;

        stopScore = true;
        stopGame = true;
        game = null;
    }


    // CONTROLS

    /**
     * This method is a listener. It activates whenever a key is pressed.
     * It controls player's movement and the ability to place a bomb.
     */
    @Override
    public void handle(KeyEvent event) {
        if (game.getNumOfPlayers() == 1) {
            switch (event.getCode()) {
                case UP:
                    game.getPlayer(1).move(Constants.Direction.UP, game);
                    break;
                case DOWN:
                    game.getPlayer(1).move(Constants.Direction.DOWN, game);
                    break;
                case RIGHT:
                    game.getPlayer(1).move(Constants.Direction.RIGHT, game);
                    break;
                case LEFT:
                    game.getPlayer(1).move(Constants.Direction.LEFT, game);
                    break;
                case SPACE:
                    game.getPlayer(1).placeBomb(game);
                    break;
                default:
                    //NOP
            }
        } else {
            switch (event.getCode()) {

                // PLAYER 1:
                case W:
                    game.getPlayer(1).move(Constants.Direction.UP, game);
                    break;
                case A:
                    game.getPlayer(1).move(Constants.Direction.LEFT, game);
                    break;
                case S:
                    game.getPlayer(1).move(Constants.Direction.DOWN, game);
                    break;
                case D:
                    game.getPlayer(1).move(Constants.Direction.RIGHT, game);
                    break;
                case SPACE:
                    game.getPlayer(1).placeBomb(game);
                    break;


                // PLAYER 2:
                case UP:
                    game.getPlayer(2).move(Constants.Direction.UP, game);
                    break;
                case DOWN:
                    game.getPlayer(2).move(Constants.Direction.DOWN, game);
                    break;
                case RIGHT:
                    game.getPlayer(2).move(Constants.Direction.RIGHT, game);
                    break;
                case LEFT:
                    game.getPlayer(2).move(Constants.Direction.LEFT, game);
                    break;
                case ENTER:
                    game.getPlayer(2).placeBomb(game);
                    break;

                default:
                    //NOP
            }
        }

        //drawGame(game, gc);
    }


    // GRAPHICS

    /**
     * Change soundButton's image when mouse enters.
     * Listener.
     * @param mouseEvent not used
     */
    public void soundButtonOnMouseEntered(MouseEvent mouseEvent) {
        if (soundOn) {
            soundButton.setImage(ImageLoader.loadImage(ImagePaths.SOUND_BUTTON_ON_CLICK));
        } else {
            soundButton.setImage(ImageLoader.loadImage(ImagePaths.SOUND_BUTTON_OFF_CLICK));
        }
    }

    /**
     * Change backButton's image when mouse enters.
     * Listener.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseEntered(MouseEvent mouseEvent) {
        backButton.setImage(ImageLoader.loadImage(ImagePaths.BACK_BUTTON_CLICK));
    }

    /**
     * Change soundButton's image when mouse exits.
     * Listener.
     * @param mouseEvent not used
     */
    public void soundButtonOnMouseExited(MouseEvent mouseEvent) {
        if (soundOn) {
            soundButton.setImage(ImageLoader.loadImage(ImagePaths.SOUND_BUTTON_ON));
        } else {
            soundButton.setImage(ImageLoader.loadImage(ImagePaths.SOUND_BUTTON_OFF));
        }
    }

    /**
     * Change backButton's image when mouse exits.
     * Listener.
     * @param mouseEvent not used
     */
    public void backButtonOnMouseExited(MouseEvent mouseEvent) {
        backButton.setImage(ImageLoader.loadImage(BACK_BUTTON));
    }



    // UPDATES:

    /**
     * Updates the score of given player.
     * @param player The given player.
     */
    private void updatePlayerScore(Player player) {
        switch (player.getNumber()) {
            case 1:
                Player1ScoreLabel.setText(player.getScore() + "");
                break;
            case 2:
                Player2ScoreLabel.setText(player.getScore() + "");
                break;
            case 3:
                Player3ScoreLabel.setText(player.getScore() + "");
                break;
            case 4:
                Player4ScoreLabel.setText(player.getScore() + "");
                break;
        }
    }

    /**
     * Updates the given player's immunity depending on the given player's life count.
     * @param player The given player.
     */
    private void updatePlayerLivesCount(Player player) {
        switch (player.getLives()) {
            case 0:
                PLAYER_OBJECTS.getPlayerStats(player.getNumber() - 1).setImage(ImageLoader.loadImage(ZERO_LIVES));
                PLAYER_OBJECTS.getPlayerPic(player.getNumber() - 1).setImage(player.getCharacter().getProfilePic(false));
                break;
            case 1:
                PLAYER_OBJECTS.getPlayerStats(player.getNumber() - 1).setImage(ImageLoader.loadImage(ONE_LIFE));
                PLAYER_OBJECTS.getPlayerPic(player.getNumber() - 1).setImage(player.getCharacter().getProfilePic(true));
                break;
            case 2:
                PLAYER_OBJECTS.getPlayerStats(player.getNumber() - 1).setImage(ImageLoader.loadImage(TWO_LIVES));
                PLAYER_OBJECTS.getPlayerPic(player.getNumber() - 1).setImage(player.getCharacter().getProfilePic(true));
                break;
            case 3:
                PLAYER_OBJECTS.getPlayerStats(player.getNumber() - 1).setImage(ImageLoader.loadImage(THREE_LIVES));
                PLAYER_OBJECTS.getPlayerPic(player.getNumber() - 1).setImage(player.getCharacter().getProfilePic(true));
                break;
            case 4:
                PLAYER_OBJECTS.getPlayerStats(player.getNumber() - 1).setImage(ImageLoader.loadImage(FOUR_LIVES));
                PLAYER_OBJECTS.getPlayerPic(player.getNumber() - 1).setImage(player.getCharacter().getProfilePic(true));
                break;
            case 5:
                PLAYER_OBJECTS.getPlayerStats(player.getNumber() - 1).setImage(ImageLoader.loadImage(FIVE_LIVES));
                PLAYER_OBJECTS.getPlayerPic(player.getNumber() - 1).setImage(player.getCharacter().getProfilePic(true));
                break;
            default:
                //NOP
        }
    }



    // SOUND CONTROL:

    /**
     * Turns the sound off.
     */
    private void turnSoundOff() {
        try {
            if (intro) {
                introPlayer.stop();
            } else {
                loopPlayer.stop();
            }
            LOGGER.log(Level.INFO, "Music stopped playing..");
            soundOn = false;
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "MediaPlayer has been not initialized or has been set to null.");
        }

    }

    /**
     * Turns the sound on.
     */
    private void turnSoundOn() {
        try {
            intro = true;
            soundOn = true;
            introPlayer.seek(Duration.ZERO);
            introPlayer.play();
            LOGGER.log(Level.INFO, "Music started playing..");
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "MediaPlayer has been not initialized or has been set to null.");
        }
    }



    // GAME OVER:

    /**
     * Ends the game.
     */
    private void gameOver() {
        if (soundOn) {
            turnSoundOff();
        }

        loopPlayer = null;
        introPlayer = null;

        stopScore = true;
        stopGame = true;
        animationTimer.stop();
        animationTimer = null;

        raiseDialog(Dialog.DialogType.GAME_OVER);
}

}
