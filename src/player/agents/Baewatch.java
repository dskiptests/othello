package player.agents.forthello;

import game.GameBoard;
import game.Position;
import game.Color;
import player.Agent;

import java.util.LinkedList;

/**
 * Created by marcus on 2016-03-11.
 */
public class Baewatch extends Agent {

    public Baewatch(Color c) {
        super(c);

    }

    private int[][] t;

    private Color ourColour = this.color;
    private Color theirColour = getOtherColor();



    @Override
    public void newGame() {
        System.out.println("We out here, looking for baes.");
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        Position bestPosition = null;
        int maxScore = 0;
        GameBoard tempBoard = null;

        for (Position p1: currentLegalPositions) {

            if(coordinateIsACorner(p1)) {
                return p1;
            }


            tempBoard = board.copyBoard();
            tempBoard.placeDisk(ourColour, p1);

            int ourMoveScore = tempBoard.getNumberOfDisksInColor(ourColour);

            if(ourMoveScore > maxScore) {
                maxScore = ourMoveScore;
                bestPosition = p1;
            }


        }


        return bestPosition;
    }

    private boolean coordinateIsACorner(Position position) {
        int row = position.row % (7);
        int col = position.column % (7);

        if(row == 0 && col == 0) return true;

        return false;
    }

    private Color getOtherColor() {
        return (this.color == Color.WHITE) ? Color.BLACK : color.WHITE;
    }

    private int caculateScore() {
        return 0;
    }
}
