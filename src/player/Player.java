package player;


import game.COLOR;
import game.Move;
import game.Board;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public abstract class Player implements Callable<Move> {


    public Board currentBoard;
    public LinkedList<Move> availableMoves;
    public final COLOR COLOR;
    public final String NAME = getClass().getSimpleName();

    @Override
    public Move call() throws Exception {
        Move move = null;
        try{
            move = nextMove();
        }catch(Exception e){
            e.printStackTrace();
        }
        return move;
    }

    public Player(COLOR color) {
        this.COLOR = color;
    }

    public COLOR getColor() {
        return this.COLOR;
    }

    @Override
    public String toString() {
        return "" + getColor();
    }

    /*
    * This Method initializes the player when a new game starts. Time slot ~ 4 seconds.
    */
    public abstract void initialize();


    /*
     * This method is called when it is the players turn to make a move. Time slot ~ 2 seconds.
     * If the player is not able to return a move within the given time slot, a random
     * move is choosen from the list of available moves.
     */
    public abstract Move nextMove();
}
