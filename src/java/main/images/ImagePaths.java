package main.images;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 27. 3. 2020
 * Time: 0:05
 */
public class ImagePaths {

    // PATHS:
    private static final String BASE_PATH = "/images/";
    private static final String BUTTON_PATH = BASE_PATH + "buttons/";
    private static final String GAME_SCENE_PATH = BASE_PATH + "game_scene/";
    private static final String MENU_SCENE_PATH = BASE_PATH + "menu_scene/";
    private static final String BLOCKS_PATH = BASE_PATH + "blocks/";
    private static final String PLAYERS_PATH = BASE_PATH + "players/";
    public static final String WHITE_PLAYER_PATH = PLAYERS_PATH + "white_player/";
    public static final String BLACK_PLAYER_PATH = PLAYERS_PATH + "black_player/";
    public static final String BLUE_PLAYER_PATH = PLAYERS_PATH + "blue_player/";
    public static final String RED_PLAYER_PATH = PLAYERS_PATH + "red_player/";
    private static final String EFFECTS_PATH = BASE_PATH + "effects/";
    private static final String BOMB_PATH = EFFECTS_PATH + "bombs/";
    private static final String BUFFS_PATH = EFFECTS_PATH + "buffs/";

    // BUTTONS:
    public static final String BACK_BUTTON = BUTTON_PATH + "BackToMenu.png";
    public static final String BACK_BUTTON_CLICK = BUTTON_PATH + "BackToMenuClicked.png";

    public static final String SOUND_BUTTON_ON = BUTTON_PATH + "SoundOn.png";
    public static final String SOUND_BUTTON_OFF = BUTTON_PATH + "SoundOff.png";
    public static final String SOUND_BUTTON_ON_CLICK = BUTTON_PATH + "SoundOnClicked.png";
    public static final String SOUND_BUTTON_OFF_CLICK = BUTTON_PATH + "SoundOffClicked.png";

    public static final String EXIT_BUTTON = BUTTON_PATH + "Exit.png";
    public static final String EXIT_BUTTON_CLICK = BUTTON_PATH + "ExitClicked.png";

    public static final String SCORE_BUTTON = BUTTON_PATH + "Score.png";
    public static final String SCORE_BUTTON_CLICK = BUTTON_PATH + "ScoreClicked.png";

    public static final String NEW_GAME_BUTTON = BUTTON_PATH + "NewGame.png";
    public static final String NEW_GAME_BUTTON_CLICK = BUTTON_PATH + "NewGameClicked.png";

    public static final String TUTORIAL_BUTTON = BUTTON_PATH + "Tutorial.png";
    public static final String TUTORIAL_BUTTON_CLICK = BUTTON_PATH + "TutorialClicked.png";

    public static final String CONTINUE_BUTTON = BUTTON_PATH + "Continue.png";
    public static final String CONTINUE_BUTTON_CLICK = BUTTON_PATH + "ContinueClicked.png";

    // BLOCKS:
    public static final String GROUND = BLOCKS_PATH + "Ground.png";
    public static final String INDESTRUCTIBLE_WALL = BLOCKS_PATH + "IndestructibleWall.png";
    public static final String CRATE = BLOCKS_PATH + "Crate.png";
    public static final String PORTAL = BLOCKS_PATH + "Portal.png";
    public static final String BOMB = BLOCKS_PATH + "Bomb.png";

    // BASE:
    public static final String ICON = BASE_PATH + "Icon.png";

    // MENU_SCENE:
    public static final String BOMBERMAN = MENU_SCENE_PATH + "BomberMan.png";

    // GAME SCENE:
    public static final String LEFT_INFO_PANEL = GAME_SCENE_PATH + "LeftPanel.png";

    // PLAYER STATS:
    public static final String ZERO_LIVES = GAME_SCENE_PATH + "0_Lives.png";
    public static final String ONE_LIFE = GAME_SCENE_PATH + "1_Lives.png";
    public static final String TWO_LIVES = GAME_SCENE_PATH + "2_Lives.png";
    public static final String THREE_LIVES = GAME_SCENE_PATH + "3_Lives.png";
    public static final String FOUR_LIVES = GAME_SCENE_PATH + "4_Lives.png";
    public static final String FIVE_LIVES = GAME_SCENE_PATH + "5_Lives.png";

    // BOMB:
    private static final String START_FLAME_1 = BOMB_PATH + "StartFlame_1.png";
    private static final String START_FLAME_2 = BOMB_PATH + "StartFlame_2.png";
    private static final String START_FLAME_3 = BOMB_PATH + "StartFlame_3.png";
    private static final String START_FLAME_4 = BOMB_PATH + "StartFlame_4.png";
    private static final String START_FLAME_5 = BOMB_PATH + "StartFlame_5.png";
    private static final String START_FLAME_6 = BOMB_PATH + "StartFlame_6.png";

    private static final String END_FLAME = "EndFlame";
    private static final String TOP = "Top_";
    private static final String RIGHT = "Right_";
    private static final String LEFT = "Left_";
    private static final String BOT = "Bottom_";

    private static final String END_RIGHT_FLAME_1 = BOMB_PATH + END_FLAME + RIGHT + "1.png";
    private static final String END_RIGHT_FLAME_2 = BOMB_PATH + END_FLAME + RIGHT + "2.png";
    private static final String END_RIGHT_FLAME_3 = BOMB_PATH + END_FLAME + RIGHT + "3.png";
    private static final String END_RIGHT_FLAME_4 = BOMB_PATH + END_FLAME + RIGHT + "4.png";
    private static final String END_RIGHT_FLAME_5 = BOMB_PATH + END_FLAME + RIGHT + "5.png";
    private static final String END_RIGHT_FLAME_6 = BOMB_PATH + END_FLAME + RIGHT + "6.png";

    private static final String END_LEFT_FLAME_1 = BOMB_PATH + END_FLAME + LEFT + "1.png";
    private static final String END_LEFT_FLAME_2 = BOMB_PATH + END_FLAME + LEFT + "2.png";
    private static final String END_LEFT_FLAME_3 = BOMB_PATH + END_FLAME + LEFT + "3.png";
    private static final String END_LEFT_FLAME_4 = BOMB_PATH + END_FLAME + LEFT + "4.png";
    private static final String END_LEFT_FLAME_5 = BOMB_PATH + END_FLAME + LEFT + "5.png";
    private static final String END_LEFT_FLAME_6 = BOMB_PATH + END_FLAME + LEFT + "6.png";

    private static final String END_TOP_FLAME_1 = BOMB_PATH + END_FLAME + TOP + "1.png";
    private static final String END_TOP_FLAME_2 = BOMB_PATH + END_FLAME + TOP + "2.png";
    private static final String END_TOP_FLAME_3 = BOMB_PATH + END_FLAME + TOP + "3.png";
    private static final String END_TOP_FLAME_4 = BOMB_PATH + END_FLAME + TOP + "4.png";
    private static final String END_TOP_FLAME_5 = BOMB_PATH + END_FLAME + TOP + "5.png";
    private static final String END_TOP_FLAME_6 = BOMB_PATH + END_FLAME + TOP + "6.png";

    private static final String END_BOT_FLAME_1 = BOMB_PATH + END_FLAME + BOT + "1.png";
    private static final String END_BOT_FLAME_2 = BOMB_PATH + END_FLAME + BOT + "2.png";
    private static final String END_BOT_FLAME_3 = BOMB_PATH + END_FLAME + BOT + "3.png";
    private static final String END_BOT_FLAME_4 = BOMB_PATH + END_FLAME + BOT + "4.png";
    private static final String END_BOT_FLAME_5 = BOMB_PATH + END_FLAME + BOT + "5.png";
    private static final String END_BOT_FLAME_6 = BOMB_PATH + END_FLAME + BOT + "6.png";

    private static final String MID_FLAME = "MidFlame";
    private static final String VERTIC = "Vertical_";
    private static final String HORIZ = "Horizontal_";

    private static final String MID_FLAME_VER_1 = BOMB_PATH + MID_FLAME + VERTIC + "1.png";
    private static final String MID_FLAME_VER_2 = BOMB_PATH + MID_FLAME + VERTIC + "2.png";
    private static final String MID_FLAME_VER_3 = BOMB_PATH + MID_FLAME + VERTIC + "3.png";
    private static final String MID_FLAME_VER_4 = BOMB_PATH + MID_FLAME + VERTIC + "4.png";
    private static final String MID_FLAME_VER_5 = BOMB_PATH + MID_FLAME + VERTIC + "5.png";
    private static final String MID_FLAME_VER_6 = BOMB_PATH + MID_FLAME + VERTIC + "6.png";

    private static final String MID_FLAME_HOR_1 = BOMB_PATH + MID_FLAME + HORIZ + "1.png";
    private static final String MID_FLAME_HOR_2 = BOMB_PATH + MID_FLAME + HORIZ + "2.png";
    private static final String MID_FLAME_HOR_3 = BOMB_PATH + MID_FLAME + HORIZ + "3.png";
    private static final String MID_FLAME_HOR_4 = BOMB_PATH + MID_FLAME + HORIZ + "4.png";
    private static final String MID_FLAME_HOR_5 = BOMB_PATH + MID_FLAME + HORIZ + "5.png";
    private static final String MID_FLAME_HOR_6 = BOMB_PATH + MID_FLAME + HORIZ + "6.png";


    public enum FlameType {
        FLAME_RIGHT_END, FLAME_LEFT_END, FLAME_TOP_END, FLAME_BOT_END, FLAME_HORIZONTAL_MID, FLAME_VERTICAL_MID, FLAME_START
    }
    public static final HashMap<FlameType, LinkedList<Image>> FLAMES = loadImages();
    private static HashMap<FlameType, LinkedList<Image>> loadImages() {
        HashMap<FlameType, LinkedList<Image>> res = new HashMap<>();

        // BOTTOM END
        LinkedList<Image> list = new LinkedList<>();
        list.add(ImageLoader.loadImage(END_BOT_FLAME_1));
        list.add(ImageLoader.loadImage(END_BOT_FLAME_2));
        list.add(ImageLoader.loadImage(END_BOT_FLAME_3));
        list.add(ImageLoader.loadImage(END_BOT_FLAME_4));
        list.add(ImageLoader.loadImage(END_BOT_FLAME_5));
        list.add(ImageLoader.loadImage(END_BOT_FLAME_6));
        res.put(FlameType.FLAME_BOT_END, list);

        // TOP END
        list = new LinkedList<>();
        list.add(ImageLoader.loadImage(END_TOP_FLAME_1));
        list.add(ImageLoader.loadImage(END_TOP_FLAME_2));
        list.add(ImageLoader.loadImage(END_TOP_FLAME_3));
        list.add(ImageLoader.loadImage(END_TOP_FLAME_4));
        list.add(ImageLoader.loadImage(END_TOP_FLAME_5));
        list.add(ImageLoader.loadImage(END_TOP_FLAME_6));
        res.put(FlameType.FLAME_TOP_END, list);

        // LEFT END
        list = new LinkedList<>();
        list.add(ImageLoader.loadImage(END_LEFT_FLAME_1));
        list.add(ImageLoader.loadImage(END_LEFT_FLAME_2));
        list.add(ImageLoader.loadImage(END_LEFT_FLAME_3));
        list.add(ImageLoader.loadImage(END_LEFT_FLAME_4));
        list.add(ImageLoader.loadImage(END_LEFT_FLAME_5));
        list.add(ImageLoader.loadImage(END_LEFT_FLAME_6));
        res.put(FlameType.FLAME_LEFT_END, list);

        // RIGHT END
        list = new LinkedList<>();
        list.add(ImageLoader.loadImage(END_RIGHT_FLAME_1));
        list.add(ImageLoader.loadImage(END_RIGHT_FLAME_2));
        list.add(ImageLoader.loadImage(END_RIGHT_FLAME_3));
        list.add(ImageLoader.loadImage(END_RIGHT_FLAME_4));
        list.add(ImageLoader.loadImage(END_RIGHT_FLAME_5));
        list.add(ImageLoader.loadImage(END_RIGHT_FLAME_6));
        res.put(FlameType.FLAME_RIGHT_END, list);


        // VERTICAL MID
        list = new LinkedList<>();
        list.add(ImageLoader.loadImage(MID_FLAME_VER_1));
        list.add(ImageLoader.loadImage(MID_FLAME_VER_2));
        list.add(ImageLoader.loadImage(MID_FLAME_VER_3));
        list.add(ImageLoader.loadImage(MID_FLAME_VER_4));
        list.add(ImageLoader.loadImage(MID_FLAME_VER_5));
        list.add(ImageLoader.loadImage(MID_FLAME_VER_6));
        res.put(FlameType.FLAME_VERTICAL_MID, list);

        // VERTICAL MID
        list = new LinkedList<>();
        list.add(ImageLoader.loadImage(MID_FLAME_HOR_1));
        list.add(ImageLoader.loadImage(MID_FLAME_HOR_2));
        list.add(ImageLoader.loadImage(MID_FLAME_HOR_3));
        list.add(ImageLoader.loadImage(MID_FLAME_HOR_4));
        list.add(ImageLoader.loadImage(MID_FLAME_HOR_5));
        list.add(ImageLoader.loadImage(MID_FLAME_HOR_6));
        res.put(FlameType.FLAME_HORIZONTAL_MID, list);


        // START
        list = new LinkedList<>();
        list.add(ImageLoader.loadImage(START_FLAME_1));
        list.add(ImageLoader.loadImage(START_FLAME_2));
        list.add(ImageLoader.loadImage(START_FLAME_3));
        list.add(ImageLoader.loadImage(START_FLAME_4));
        list.add(ImageLoader.loadImage(START_FLAME_5));
        list.add(ImageLoader.loadImage(START_FLAME_6));
        res.put(FlameType.FLAME_START, list);

        return res;
    }

    // GAME OVER DIALOG
    public static final String GAME_OVER = BASE_PATH + "other/GameOver.png";

    // TUTORIAL
    public static final String TUTORIAL = BASE_PATH + "tutorial_scene/tutorial.png";

    // BUFFS
    public static final String LIFE_UP = BUFFS_PATH + "LifeUp.png";
    public static final String LIFE_DOWN = BUFFS_PATH + "LifeDown.png";
    public static final String IMMORTALITY = BUFFS_PATH + "Immortality.png";
    public static final String BOMB_UPGRADE = BUFFS_PATH + "BombUpgrade.png";
    public static final String BOMB_DEGRADE = BUFFS_PATH + "BombDegrade.png";

}
