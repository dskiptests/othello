package player;


import game.COLOR;
import game.Position;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public abstract class Player implements Callable<Position> {


    public Board currentBoard;
    public LinkedList<Position> availablePositions;
    public final COLOR COLOR;
    public final String NAME = getClass().getSimpleName();

    @Override
    public Position call() throws Exception {
        Position position = null;
        try{
            position = nextMove();
        }catch(Exception e){
            e.printStackTrace();
        }
        return position;
    }

    public Player(COLOR color) {
        this.COLOR = color;
    }


    @Override
    public String toString() {
        return "" + COLOR;
    }

    /*
    * This Method initializes the player when a new game starts. Time slot ~ 4 seconds.
    */
    public abstract void newGame();


    /*
     * This method is called when it is the players turn to make a move. Time slot ~ 2 seconds.
     * If the player is not able to return a move within the given time slot, a random
     * move is choosen from the list of available moves.
     */
    public abstract Position nextMove() throws InterruptedException;
}
