package player;


import game.Game;
import game.Move;
import gui.GameBoard;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Callable;

public abstract class Player implements Callable<Move> {


    public GameBoard board;
    public LinkedList<Move> availableMoves;

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

    /*
    * This Method initializes the player when a new game starts. Time slot = 20 s.
    */
    public abstract void initialize();


    /*
     * This method is called when it is the players turn to make a move. Time slot = 2 s.
     * If the player is not able to return a move within the given time slot, a random
     * move is choosen from the list of available moves.
     */
    public abstract Move nextMove();
}
