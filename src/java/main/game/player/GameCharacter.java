package main.game.player;

import javafx.scene.image.Image;
import main.images.ImageLoader;

import java.util.HashMap;
import java.util.LinkedList;

import static main.game.player.GameCharacter.Color.*;
import static main.game.player.PlayerPose.*;
import static main.images.ImagePaths.*;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 29. 4. 2020
 * Time: 13:13
 */
public class GameCharacter {
    private Color color;
    private PlayerPose currPose;
    private HashMap<PlayerPose, Image> images;

    public static final GameCharacter RED_CHARACTER = new GameCharacter(RED);
    public static final GameCharacter BLACK_CHARACTER = new GameCharacter(BLACK);
    public static final GameCharacter BLUE_CHARACTER = new GameCharacter(BLUE);
    public static final GameCharacter WHITE_CHARACTER = new GameCharacter(WHITE);

    public enum Color {
        RED("Red"), BLUE("Blue"), WHITE("White"), BLACK("Black");

        private String name;

        Color(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Sets the chosen character and her initial pose.
     * @param color The chosen character.
     */
    private GameCharacter(Color color) {
        this.color = color;
        this.images = loadImages();
        currPose = LOOK_DOWN;
    }

    /**
     * Creates a list of characters.
     * @return List of characters.
     */
    public static LinkedList<GameCharacter> createListOfGameCharacters() {
        LinkedList<GameCharacter> gameCharacters = new LinkedList<>();
        gameCharacters.add(RED_CHARACTER);
        gameCharacters.add(BLACK_CHARACTER);
        gameCharacters.add(BLUE_CHARACTER);
        gameCharacters.add(WHITE_CHARACTER);
        return gameCharacters;
    }

    /**
     * Finds the wanted pose image.
     * @param pose The wanted pose.
     * @return Pose Image.
     */
    private Image getPoseImage(PlayerPose pose) {
        return images.get(pose);
    }

    /**
     * Gets the Player's current pose.
     * @return The Player's current pose.
     */
    public PlayerPose getCurrPose() {
        return currPose;
    }

    /**
     * Gets the Player's list of images.
     * @return The list of Player's images.
     */
    public HashMap<PlayerPose, Image> getImages() {
        return images;
    }

    /**
     * Assigns name of the character to its profile picture.
     * @param name Name of the character.
     * @return GameCharacter that fits the name.
     */
    public static GameCharacter findGameCharacter(String name) {
        if (name == null) {
            return null;
        }
        switch (name) {
            case "Red":
                return RED_CHARACTER;
            case "Blue":
                return BLUE_CHARACTER;
            case "Black":
                return BLACK_CHARACTER;
            case "White":
                return WHITE_CHARACTER;
            default:
                return null;
        }
    }

    /**
     * Gets name of the characters.
     * @return List of characters' names.
     */
    public static LinkedList<String> getNames() {
        LinkedList<GameCharacter> list = createListOfGameCharacters();
        LinkedList<String> res = new LinkedList<>();

        for (GameCharacter character : list) {
            res.add(character.getColor().getName());
        }

        return res;
    }

    /**
     * Sets a profile picture for the characters in game.
     * @param alive Character is alive or dead.
     * @return Profile picture.
     */
    public Image getProfilePic(boolean alive) {
        if (alive) {
            return getPoseImage(PROFILE_PIC);
        } else {
            return getPoseImage(PROFILE_PIC_DEATH);
        }
    }

    /**
     * Appoints paths for the images of poses of the same color to their character.
     * @return HashMap of images.
     */
    private HashMap<PlayerPose, Image> loadImages() {
        String path = null;

        switch (color) {
            case RED:
                path = RED_PLAYER_PATH;
                break;
            case BLUE:
                path = BLUE_PLAYER_PATH;
                break;
            case BLACK:
                path = BLACK_PLAYER_PATH;
                break;
            case WHITE:
                path = WHITE_PLAYER_PATH;
                break;
        }

        return createHashMapOfImages(path);
    }

    /**
     * Gets poses for the characters movement in game.
     * @param path The assigned image path for the character.
     * @return HashMap of images.
     */
    private HashMap<PlayerPose, Image> createHashMapOfImages(String path) {
        LinkedList<PlayerPose> poses = getAllPoses();
        HashMap<PlayerPose, Image> res = new HashMap<>();

        for (PlayerPose pose : poses) {
            res.put(pose, ImageLoader.loadImage(path + pose.getImageName()));
        }

        return res;
    }

    /**
     * Gets the color of the game character.
     * @return The color of game character.
     */
    private Color getColor() {
        return color;
    }

    /**
     * Gets the image for current pose.
     * @return the image for current pose.
     */
    public Image getCurrPoseImage() {
        return getPoseImage(currPose);
    }

    /**
     * Sets the current Pose to the given pose.
     * @param currPose The given pose.
     */
    public void setCurrPose(PlayerPose currPose) {
        this.currPose = currPose;
    }

}
