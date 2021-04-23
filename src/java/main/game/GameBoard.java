package main.game;

import main.game.blocks.Block;
import main.game.blocks.Crate;
import main.game.blocks.Ground;
import main.game.blocks.IndestructibleWall;
import main.game.effects.Effect;
import main.game.effects.Effects;
import main.game.effects.buffs.*;
import main.mapgenerator.Map;

import java.util.Random;
import java.util.Scanner;

import static main.Constants.GAME_BOARD_SIZE;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 26. 3. 2020
 * Time: 13:57
 */
public class GameBoard {
    private volatile Block[][] blocks;
    private volatile Effects[][] effects;
    private int size;

    public static final int BOTTOM_BORDER = GAME_BOARD_SIZE - 2;
    public static final int LEFT_BORDER = 1;
    public static final int TOP_BORDER = LEFT_BORDER;
    public static final int RIGHT_BORDER = BOTTOM_BORDER;


    // CONSTRUCTORS:

    /**
     * The Constructor of GameBoard.
     * @param pathToMap The path to map file.
     */
    public GameBoard(String pathToMap) {
        this.blocks = loadMap(pathToMap);
        this.size = blocks.length;
        this.effects = new Effects[size][size]; //Effect[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                effects[i][j] = new Effects();
            }
        }
    }

    /**
     * The Constructor of GameBoard with random map.
     */
    public GameBoard() {
        this.size = GAME_BOARD_SIZE;
        generateMap();
        this.effects = new Effects[size][size]; //Effect[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                effects[i][j] = new Effects();
            }
        }
    }



    // GETTERS:

    /**
     * Gets the size of GameBoard.
     * @return The size of GameBoard.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the array of blocks.
     * @return The array of blocks.
     */
    public Block[][] getBlocks() {
        return blocks;
    }

    /**
     * Gets the array of effects.
     * @return The array of effects.
     */
    public Effects[][] getEffects() {
        return effects;
    }



    // SETTERS:

    /**
     * Sets the array of blocks to the given array of blocks.
     * @param blocks The given array of blocks.
     */
    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    /**
     * Sets the array of effects to the given array of effects.
     * @param effects The given array of effects.
     */
    public void setEffects(Effects[][] effects) {
        this.effects = effects;
    }


    /**
     * Generates random map from MapGenerator.
     */
    private void generateMap() {
        Map newMap = new Map(this.size, 1);

        newMap.generateMap(3);

        char[][] map = newMap.getCharMap();
        this.blocks = new Block[size][size];

        for (int j = 0; j < size; j++) {
            char[] line = map[j];
            for (int i = 0; i < size; i++) {
                char symbol = line[i];
                switch (symbol) {
                    case 'W':
                        this.blocks[j][i] = new IndestructibleWall();
                        break;
                    case '_':
                        this.blocks[j][i] = new Ground();
                        break;
                    case 'I':
                        this.blocks[j][i] = new Crate();
                        break;
                    default:
                        System.err.println("No image printed");
                        break;
                }
            }
        }
    }

    /**
     * Loads map for game from text file in defined structure.
     * @param fileName The name of text file.
     * @return The map in form of two-dimensional array of Blocks.
     */
    private static Block[][] loadMap(String fileName) {
        try {
            //Scanner sc = new Scanner(new File(fileName));   // vytvori novy scanr pro cteni z text souboru
            Scanner sc = new Scanner(GameBoard.class.getResourceAsStream(fileName));   // vytvori novy scanr pro cteni z text souboru
            int size = Integer.parseInt(sc.nextLine());
            Block[][] board = new Block[size][size];

            for (int j = 0; j < size; j++) {
                char[] line = sc.nextLine().toCharArray();
                for (int i = 0; i < size; i++) {
                    char symbol = line[i];
                    switch (symbol) {
                        case 'W':
                            board[j][i] = new IndestructibleWall();
                            break;
                        case '_':
                            board[j][i] = new Ground();
                            break;
                        case 'I':
                            board[j][i] = new Crate();
                            break;
                        default:
                            System.err.println("No image printed");
                            break;
                    }
                }
            }
            return board;
        } catch (Exception e) {
            System.err.println("File not Found");
            System.exit(-1);
            return null;
        }
    }

    /**
     * Adds a new effect on given coordinates in array of effects.
     * @param newEffect The new effect.
     * @param posX The coordinate X.
     * @param posY The coordinate Y.
     */
    public void addEffect(Effect newEffect, int posX, int posY) {
        getEffects()[posX][posY].add(newEffect);
    }

    /**
     * Removes the given effect from the array of effects if found.
     * @param effect The given effect.
     */
    public void removeEffect(Effect effect) {
        for (int i = 0; i < effects.length; i++) {
            for (int j = 0; j < effects.length; j++) {
                if (!effects[i][j].isEmpty()) {
                    effects[i][j].remove(effect);
                }
            }
        }
    }

    /**
     * Destroys the block on given coordinates in the array of blocks.
     * @param posX The coordinate X.
     * @param posY The coordinate Y.
     */
    public void destroyBlock(int posX, int posY) {
        this.blocks[posX][posY] = new Ground();

        Random random = new Random();
        int i = random.nextInt(1000);

        if (i > 800) {
            random = new Random();
            int j = random.nextInt(1000);

            if (j < 300) {
                this.effects[posX][posY].add(new BombUpgrade());
            } else if (j < 600) {
                this.effects[posX][posY].add(new BombDegrade());
            } else if (j < 750) {
                this.effects[posX][posY].add(new LifeUp());
            } else if (j < 900) {
                this.effects[posX][posY].add(new Immortality());
            } else {
                this.effects[posX][posY].add(new LifeDown());
            }
        }
    }
}
