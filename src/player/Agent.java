package player;


import game.Color;
import game.GameBoard;
import game.Position;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public abstract class Agent implements Callable<Position> {

    public GameBoard currentBoard;
    public LinkedList<Position> currentLegalPositions;

    public final Color color;
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

    public Agent(Color color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return NAME + " (" + color + ")";
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
     * @param board A copy of the current Board. The same variable as the global board in this class.
     * @param currentLegalPositions A copy of the legal positions. Same as the gloal list.
     * @return A choosen position for the next turn.
     * @throws InterruptedException
     */
    public abstract Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException;
}
