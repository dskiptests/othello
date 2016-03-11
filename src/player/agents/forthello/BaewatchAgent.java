package player.agents.forthello;

import game.GameBoard;
import game.Position;
import game.Color;
import player.Player;

import java.util.LinkedList;

/**
 * Created by marcus on 2016-03-11.
 */
public class BaewatchAgent extends Player {

    public BaewatchAgent(Color c) {
        super(c);

    }

    private int[][] t;

    private Color ourColour = this.COLOR;
    private Color theirColour = getOtherColor();



    @Override
    public void newGame() {
        t = new int[8][8];

        t[0][0] = 99;
        t[0][1] = -8;
        t[0][2] = 8;
        t[0][3] = 6;
        t[0][4] = 6;
        t[0][5] = 8;
        t[0][6] = -8;
        t[0][7] = 99;

        t[1][0] = -8;
        t[1][1] = -24;
        t[1][2] = -4;
        t[1][3] = -3;
        t[1][4] = -3;
        t[1][5] = -4;
        t[1][6] = -24;
        t[1][7] = -8;

        t[2][0] = 8;
        t[2][1] = 6;
        t[2][2] = 7;
        t[2][3] = 4;
        t[2][4] = 4;
        t[2][5] = 7;
        t[2][6] = 6;
        t[2][7] = 8;

        t[3][0] = 6;
        t[3][1] = -3;
        t[3][2] = 4;
        t[3][3] = 0;
        t[3][4] = 0;
        t[3][5] = 4;
        t[3][6] = -3;
        t[3][7] = 6;

        t[4][0] =  6;
        t[4][1] =  -3;
        t[4][2] =  4;
        t[4][3] =  0;
        t[4][4] =  0;
        t[4][5] =  4;
        t[4][6] =  -3;
        t[4][7] =  6;

        t[5][0] = 8;
        t[5][1] = 6;
        t[5][2] = 7;
        t[5][3] = 4;
        t[5][4] = 4;
        t[5][5] = 7;
        t[5][6] = 6;
        t[5][7] = 8;

        t[6][0] = -8;
        t[6][1] = -24;
        t[6][2] = -4;
        t[6][3] = -3;
        t[6][4] = -3;
        t[6][5] = -4;
        t[6][6] = -24;
        t[6][7] = -8;

        t[7][0] = 99;
        t[7][1] = -8;
        t[7][2] = 8;
        t[7][3] = 6;
        t[7][4] = 6;
        t[7][5] = 8;
        t[7][6] = -8;
        t[7][7] = 99;

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        Position bestPosition = null;
        int maxScore = 0;
        GameBoard tempBoard = null;

        for (Position p1: currentLegalPositions) {

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

    private Color getOtherColor() {
        return (this.COLOR == Color.WHITE) ? Color.BLACK : COLOR.WHITE;
    }

    private int caculateScore() {
        return 0;
    }
}
