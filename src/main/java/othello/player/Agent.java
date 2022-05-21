package othello.player;


import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class Agent implements Callable<Position> {


    private final String PLAYER_NAME = this.getClass().getSimpleName();
    private final Color color;

    public GameBoard currentBoard;
    public List<Position> currentLegalPositions;
    private long availableTime;

    @Override
    public final Position call() throws Exception {
        Position position = null;
        try{
            position = nextMove(this.currentBoard, this.currentLegalPositions);
        } catch(Exception e){
            System.out.println(e);
        }
        return position;
    }

    public Agent(final Color color) {
        this.color = color;
    }


    @Override
    public final String toString() {
        return PLAYER_NAME + " (" + color + ")";
    }

    /**
     *  This Method initializes the othello.player when a new othello.game starts. Time slot ~ 4 seconds.
     */



    public final String name() {
        return PLAYER_NAME;
    }

    public final Color color() {
        return this.color;
    }

    public abstract void newGame();

    /**
     * This method is called when it is the players turn to make a move. Time slot ~ 2 seconds.
     * If the othello.player is not able to return a move within the given time slot, a random
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
    public abstract Position nextMove(final GameBoard board, final List<Position> currentLegalPositions) ;

    public final void setAvailableTimeLeft(final long availableTime) {
        this.availableTime = availableTime;
    }

    protected long availableTime() {
        return this.availableTime;
    }
}
