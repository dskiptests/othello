package player;


import game.Color;
import game.GameBoard;
import game.Position;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public abstract class Player implements Callable<Position> {


    public GameBoard currentBoard;
    public LinkedList<Position> currentLegalPositions;

    public final Color COLOR;
    public final String NAME = this.getClass().getSimpleName();

    @Override
    public Position call() throws Exception {
        Position position = null;
        try{
            position = nextMove(this.currentBoard, this.currentLegalPositions);
        }catch(Exception e){
            e.printStackTrace();
        }
        return position;
    }

    public Player(Color color) {
        this.COLOR = color;
    }


    @Override
    public String toString() {
        return "" + COLOR;
    }

    /**
     *  This Method initializes the player when a new game starts. Time slot ~ 4 seconds.
     */
    public abstract void newGame();


    /**
     * This method is called when it is the players turn to make a move. Time slot ~ 2 seconds.
     * If the player is not able to return a move within the given time slot, a random
     * move is choosen from the list of available moves. Any time not used will be added
     * to the next time this method is called.
     *
     * Before this method is called, all global variables are updated.
     *
     * @return A choosen position for the next turn.
     * @throws InterruptedException
     */

    public abstract Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException;
}
