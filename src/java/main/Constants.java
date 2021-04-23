package main;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 26. 3. 2020
 * Time: 13:36
 */
public class Constants {
    public enum Direction {
        UP(0,-1), DOWN(0,1), RIGHT(1,0), LEFT(-1,0);

        private int x;
        private int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    // SCENE:
    public static final int SCENE_WIDTH = 912;
    public static final int SCENE_HEIGHT = 712;
    public static int GAME_BOARD_SIZE = 19;
    public static final int BLOCK_SIZE = 38;

    // PLAYER
    public static final int LIFE_COUNT = 5;

    // BOMB
    public static final int MAX_FLAME_LENGTH = 5;
    public static final int MIN_FLAME_LENGTH = 1;
    public static final int TIME_TILL_EXPLOSION = 2;

    // FXML:
    public static final String FXML_GAME = "/fxml/game.fxml";
    public static final String FXML_MENU = "/fxml/menu.fxml";
    public static final String FXML_SCORE = "/fxml/scoreboard.fxml";
    public static final String FXML_TUTORIAL = "/fxml/tutorial.fxml";

    // MAP FILES:
    private static final String MAP_PATH = "/maps/";
    public static final String MAP_LEVEL_1 = MAP_PATH + "map_level_1.txt";
    public static final String MAP_LEVEL_2 = MAP_PATH + "map_level_2.txt";
    public static final String MAP_LEVEL_3 = MAP_PATH + "map_level_3.txt";
    public static final String MAP_LEVEL_4 = MAP_PATH + "map_level_4.txt";
    public static final String MAP_LEVEL_5 = MAP_PATH + "map_level_5.txt";

    // FLAME ANIMATION INDEXES:
    public static final int FLAME_INDEX_START = 0;
    public static final int FLAME_INDEX_MID_HORIZONTAL = 1;
    public static final int FLAME_INDEX_MID_VERTICAL = 2;
    public static final int FLAME_INDEX_TOP = 3;
    public static final int FLAME_INDEX_RIGHT = 4;
    public static final int FLAME_INDEX_BOT = 5;
    public static final int FLAME_INDEX_LEFT = 6;

    // SCORE:
    public static final int SCORE_ENEMY_HIT = 500;
    public static final int SCORE_HIT_BY_MYSELF = -1000;
    public static final int SCORE_HIT_BY_ENEMY = -200;
    public static final int SCORE_PER_TIMER = 1;
    public static final int SCORE_TIMER_PERIOD = 100;
    public static final int SCORE_BLOCK_DESTROYED = 300;
    public static final int SCORE_BOMB_PLACED = 50;

    // SCORE FILE:
    public static final String SCORE_TXT = "/score.txt";
}
