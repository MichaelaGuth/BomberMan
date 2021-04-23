package main.game.player;


import javafx.util.Pair;
import main.Constants;
import main.game.Game;
import main.game.GameBoard;
import main.game.GameUtils;

import java.util.*;

import static main.game.GameController.game;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 23. 4. 2020
 * Time: 22:16
 */
public class AI extends Player {    // TODO
    private Timer timer;

    public AI(GameCharacter character, int index, int number) {
        super(character, "AI_" + index, number);
        timer = new Timer();

        /*
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                makeAIMove(game);
            }
        }, 500, 200);*/
    }

    @Override
    public boolean isAI() {
        return true;
    }

    public void makeAIMove(Game game) {
        Player opponent = findNearestOpponent(game);

        Constants.Direction movement = getBestMove(opponent, game.getGameBoard());

        if (movement != null) {
            movement = correctionOfDirWhenCloseContact(movement, opponent, game.getGameBoard());
            move(movement, game);
        } else {

        }
    }

    private Constants.Direction correctionOfDirWhenCloseContact(Constants.Direction direction, Player nearestPlayer, GameBoard gameBoard) {
        if ((getPositionX()+direction.getX()) == nearestPlayer.getPositionX() && (getPositionY()+direction.getY()) == nearestPlayer.getPositionY()) {
            List<Constants.Direction> directions = Collections.synchronizedList(Arrays.asList(Constants.Direction.values()));



            List<Pair<Constants.Direction, Double>> distances = new LinkedList<>();

            for(Constants.Direction dir : directions) {
                if(!GameUtils.checkCoordinatesArePassable(dir.getX()+getPositionX(), dir.getY()+getPositionY(), gameBoard)){
                    directions.remove(dir);
                } else {
                    distances.add(new Pair<>(dir, distanceFromPlayer(nearestPlayer)));
                }
            }

            if(directions.size() == 1) {
                return directions.get(0);
            }

            distances.sort(new Comparator<Pair<Constants.Direction, Double>>() {
                @Override
                public int compare(Pair<Constants.Direction, Double> o1, Pair<Constants.Direction, Double> o2) {
                    double result = o1.getValue() - o2.getValue();
                    if (result > 0) {
                        return 1;
                    } else if (result < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

            return distances.get(distances.size() -1).getKey();

        } else {
            return direction;
        }
    }

    private Player findNearestOpponent(Game game){
        double length = Double.MAX_VALUE;
        Player nearest = null;

        List<Pair<Player, Double>> distances = new ArrayList<>();
        for (Player p : game.getAlivePlayers()){
            if (p != this) {
                distances.add(new Pair<>(p, distanceFromPlayer(p)));
            }
        }

        distances.sort(new Comparator<Pair<Player, Double>>() {
            @Override
            public int compare(Pair<Player, Double> o1, Pair<Player, Double> o2) {
                double result = o1.getValue() - o2.getValue();
                if (result > 0) {
                    return 1;
                } else if (result < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        for (Pair<Player, Double> pair : distances) {
            if (pair.getValue() >= 1) {
                return pair.getKey();
            }
        }

        return distances.get(distances.size() - 1).getKey();
    }

    private Constants.Direction getBestMove(Player p, GameBoard gameBoard) {
        Constants.Direction result = null;

        List<Constants.Direction> possibleDirections = directionsToPlayer(p);

        for(Constants.Direction d : possibleDirections) {
            int tmpX = getPositionX() + d.getX();
            int tmpY = getPositionY() + d.getY();
            if (GameUtils.checkCoordinatesAreInGameBoard(tmpX, tmpY)) {
                if(GameUtils.checkCoordinatesArePassable(tmpX, tmpY, gameBoard)) {
                    result = d;
                    break;
                }
            }
        }

        return result;
    }

    private List<Constants.Direction> directionsToPlayer(Player p) {
        List<Constants.Direction> result = new LinkedList<>();

        int vecX = p.getPositionX() - getPositionX();
        int vecY = p.getPositionY() - getPositionY();
        boolean oneIsZero = vecX == 0 || vecY == 0;

        // Normalize vector
        if(vecX != 0){
            vecX = vecX/Math.abs(vecX);
        }

        if(vecY != 0){
            vecY = vecY/Math.abs(vecY);
        }

        for (Constants.Direction d : Constants.Direction.values()) {
            if (oneIsZero) {
                if (d.getX() == vecX && d.getY() == vecY) {
                    result.add(d);
                    break;
                }
            } else {
                if (d.getX() == vecX || d.getY() == vecY) {
                    result.add(d);
                }
            }
        }

        return result;
    }



    private double distanceFromPlayer(Player p) {
        double res = Double.MAX_VALUE;

        res = Math.sqrt(Math.pow(p.getPositionX()-getPositionX(),2) + Math.pow(p.getPositionY() - getPositionY(), 2));

        return res;
    }
}
