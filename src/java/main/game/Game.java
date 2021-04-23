package main.game;


import main.game.blocks.Block;
import main.game.effects.Effects;
import main.game.player.AI;
import main.game.player.GameCharacter;
import main.game.player.Player;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.game.player.GameCharacter.createListOfGameCharacters;


/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 26. 3. 2020
 * Time: 13:57
 */
public class Game {

    private GameBoard gameBoard;
    private int numOfRounds;
    private int numOfPlayers;
    private int numOfAIs;
    private List<Player> alivePlayers;
    private List<Player> deadPlayers;

    private static Logger LOGGER = Logger.getLogger(Game.class.getName());

    // CONSTRUCTORS:

    /**
     * The constructor of Game.
     * @param numOfAIs The number of AIs.
     * @param numOfRounds The number of Rounds.
     * @param gameBoard The game board.
     * @param player1Character The player one's character.
     * @param player1Username The player one's username.
     */
    public Game(int numOfAIs, int numOfRounds, GameBoard gameBoard, GameCharacter player1Character, String player1Username) {
        LinkedList<GameCharacter> gameCharacters = createListOfGameCharacters();
        gameCharacters.remove(player1Character);

        this.numOfPlayers = 1;
        this.numOfRounds = numOfRounds;
        this.numOfAIs = numOfAIs;
        this.gameBoard = gameBoard;

        if (player1Username.equals("")) {
            player1Username = "Randomák";
        }

        deadPlayers = Collections.synchronizedList(new ArrayList<>());
        alivePlayers = Collections.synchronizedList(new ArrayList<>());
        alivePlayers.add(new Player(player1Character, player1Username, 1));

        switch (numOfAIs) {
            case 1:
                alivePlayers.add(new AI(gameCharacters.get(0), 1, 2));
                break;
            case 2:
                alivePlayers.add(new AI(gameCharacters.get(0), 1, 2));
                alivePlayers.add(new AI(gameCharacters.get(1), 2, 3));
                break;
            case 3:
                alivePlayers.add(new AI(gameCharacters.get(0), 1, 2));
                alivePlayers.add(new AI(gameCharacters.get(1), 2, 3));
                alivePlayers.add(new AI(gameCharacters.get(2), 3, 4));
                break;
            default:
                //NOP
        }
    }

    /**
     * The constructor of Game.
     * @param numOfAIs The number of AIs.
     * @param numOfRounds The number of rounds.
     * @param gameBoard The game board.
     * @param numOfPlayers The number of players.
     * @param alivePlayers The list of all players.
     */
    public Game(GameBoard gameBoard, int numOfRounds, int numOfPlayers, int numOfAIs, List<Player> alivePlayers) {
        this.gameBoard = gameBoard;
        this.numOfRounds = numOfRounds;
        this.numOfPlayers = numOfPlayers;
        this.numOfAIs = numOfAIs;
        this.alivePlayers = alivePlayers;
        this.deadPlayers = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * The constructor of Game.
     * @param numOfAIs The number of AIs.
     * @param numOfRounds The number of Rounds.
     * @param gameBoard The game board.
     * @param player1Character The player one's character.
     * @param player1Username The player one's username.
     * @param player2Character The player two's character.
     * @param player2Username The player two's username.
     */
    public Game(int numOfAIs, int numOfRounds, GameBoard gameBoard, GameCharacter player1Character, GameCharacter player2Character, String player1Username, String player2Username) {
        LinkedList<GameCharacter> gameCharacters = createListOfGameCharacters();
        gameCharacters.remove(player1Character);
        gameCharacters.remove(player2Character);

        this.numOfAIs = numOfAIs;
        this.gameBoard = gameBoard;
        this.numOfPlayers = 2;
        this.numOfRounds = numOfRounds;

        if (player1Username.equals("")) {
            player1Username = "Randomák_01";
        }

        if (player2Username.equals("")) {
            player2Username = "Randomák_02";
        }


        deadPlayers = Collections.synchronizedList(new ArrayList<>());
        alivePlayers = Collections.synchronizedList(new ArrayList<>());
        alivePlayers.add(new Player(player1Character, player1Username, 1));
        alivePlayers.add(new Player(player2Character, player2Username, 2));

        switch (numOfAIs) {
            case 1:
                alivePlayers.add(new AI(gameCharacters.get(0), 1, 3));
                break;
            case 2:
                alivePlayers.add(new AI(gameCharacters.get(0), 1, 3));
                alivePlayers.add(new AI(gameCharacters.get(1), 2, 4));
                break;
            default:
                //NOP
        }
    }



    // GETTERS:

    /**
     * Gets the Game's game board.
     * @return The Game's game board.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Gets the Game's array of Blocks.
     * @return The Game's array of Blocks.
     */
    public Block[][] getBlocks() {
        return gameBoard.getBlocks();
    }

    /**
     * Gets the Game's array of effects.
     * @return The Game's array of effects.
     */
    public Effects[][] getEffects() {
        return gameBoard.getEffects();
    }

    /**
     * Gets the Game's number of rounds.
     * @return The Game's number of rounds.
     */
    int getNumOfRounds() {
        return numOfRounds;
    }

    /**
     * Gets the Game's number of real players (not AI).
     * @return The Game's number of real players.
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Gets the Game's number of AIs.
     * @return The Game's number of AIs.
     */
    int getNumOfAIs() {
        return numOfAIs;
    }

    /**
     * Gets the Game's list of alive players.
     * @return The Game's list of alive players.
     */
    public List<Player> getAlivePlayers() {
        return alivePlayers;
    }

    /**
     * Gets the Game's list of dead players.
     * @return The Game's list of dead players.
     */
    List<Player> getDeadPlayers() {
        return deadPlayers;
    }

    /**
     * Gets list of all players - dead or alive.
     * @return List of all players.
     */
    List<Player> getAllPlayers() {
        List<Player> res = Collections.synchronizedList(new ArrayList<>());
        res.addAll(getAlivePlayers());
        res.addAll(getDeadPlayers());
        return res;
    }

    /**
     * Gets list of all local players who are not AIs.
     * @return List of local players.
     */
    public ArrayList<Player> getRealPlayers() {
        ArrayList<Player> res = new ArrayList<>();
        for (Player p :
                getAllPlayers()) {
            if (!p.isAI()) {
                res.add(p);
            }
        }
        return res;
    }

    /**
     * Gets player by his number.
     * @param number The given player's number.
     * @return Returns player.
     */
    public Player getPlayer(int number) {
        for (Player p : getAllPlayers()) {
            if (p.getNumber() == number) {
                return p;
            }
        }
        return null;
    }





    // SETTER:
    /**
     * Sets player to its start position.
     */
    void setStartParameters() {
        LOGGER.log(Level.INFO, "Setting start parameters for players...");
        for (Player p : getAlivePlayers()) {
            p.setStartParameters();
        }
    }



    // ACTIONS:
    /**
     * Checks the number of alive local players to decide whether the game is over.
     * @return TRUE if game is over, FALSE if not
     */
    boolean checkIfGameOver() {
        boolean gameOver = false;

        if (getAlivePlayers().size() == 1) {
            gameOver = true;
        }

        boolean check = true;
        if (getAlivePlayers().size() == 2 || getAlivePlayers().size() == 3) {
            for (Player p : getAlivePlayers()) {
                if (!p.isAI()) {
                    check = false;
                }
            }

            if (check) {
                gameOver = true;
            }
        }

        return gameOver;
    }

    /**
     * Updates players status - dead or alive.
     */
    void update() {
        updateAlivePlayers();
    }

    /**
     * Updates the list of alive and dead players.
     */
    private void updateAlivePlayers() {
        List<Player> playersToRemove = new ArrayList<>();
        for (Player p : getAlivePlayers()) {
            if (!p.isAlive()) {
                LOGGER.log(Level.INFO,"Player named: " + p.getUsername() + "has died.");
                getDeadPlayers().add(p);
                playersToRemove.add(p);
            }
        }

        for (Player p :
                playersToRemove) {
            getAlivePlayers().remove(p);
        }
    }

}
