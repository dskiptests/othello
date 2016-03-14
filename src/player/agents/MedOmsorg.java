
package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by per on 3/11/16.
 */
public class MedOmsorg extends Agent {
    private Random rand = new Random();

    public MedOmsorg(Color color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        GameBoard currentBoard;
        Position bestMove = currentLegalPositions.get(rand.nextInt(currentLegalPositions.size()));
        Color color = getOppositeColor(this.color);
        int depth = 5;
        int currentScore;
        double bestScore = -99;
        long startTime = System.nanoTime();
        long timeslice;
        long remainingTime;
        long timeout = 1750000000;
        int movesRemaining = currentLegalPositions.size();


        for (Position p : currentLegalPositions) {
            currentBoard = board.copyBoard();
            remainingTime = timeout - (startTime - System.nanoTime());
            timeslice = remainingTime / movesRemaining;
            currentScore = negamax(currentBoard, currentBoard.getAllLegalPositions(color), color, depth, timeslice);
            if (currentScore > bestScore) {
                bestMove = p;
                bestScore = currentScore;
            }
            movesRemaining--;
        }

        return bestMove;
    }

    public int negamax(GameBoard board, LinkedList<Position> currentLegalPositions, Color color, int depth, long timeout) {
        if (currentLegalPositions.size() == 0) {
            int myScore = board.getNumberOfDisksInColor(color);
            int oppScore = board.getNumberOfDisksInColor(getOppositeColor(color));
            return myScore - oppScore;
        }
        if (depth == 0) {
            return -evaluateMove(board, color);
        }
        if (timeout <= 25000){
            System.err.println("Timeout reached: " + timeout + " at depth " + depth);
            return -evaluateMove(board, color);
        }
        int bestScore = -99;
        int currentScore;
        GameBoard currentBoard;

        long startTime = System.nanoTime();
        long timeslice;
        long remainingTime;
        int movesRemaining = currentLegalPositions.size();

        for (Position p : currentLegalPositions) {
            remainingTime = timeout - (startTime - System.nanoTime());
            timeslice = remainingTime / movesRemaining;
            currentBoard = board.copyBoard();
            currentScore = negamax(currentBoard, currentBoard.getAllLegalPositions(getOppositeColor(color)), getOppositeColor(color), depth-1, timeslice);
            if (currentScore > bestScore) {
                bestScore = currentScore;
            }
            movesRemaining--;
        }

        return -bestScore;
    }

    private int evaluateMove(GameBoard board, Color color) {
        int myScore, oppScore;
        myScore = oppScore = 0;

        int[][] weights = {{20, -3, 11, 8, 8, 11, -3, 20},
                {-3, -7, -4, 1, 1, -4, -7, -3},
                {11, -4,  2, 2, 2,  2, -4, 11},
                { 8,  1,  2,-3,-3,  2,  1,  8},
                { 8,  1,  2,-3,-3,  2,  1,  8},
                {11, -4,  2, 2, 2,  2, -4, 11},
                {-3, -7, -4, 1, 1, -4, -7, -3},
                {20, -3, 11, 8, 8, 11, -3, 20}};

        Color[][] cm = board.getBoardMatrix();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cm[i][j] == color) {
                    myScore += weights[i][j];
                } else if (cm[i][j] == getOppositeColor(color)) {
                    oppScore += weights[i][j];
                }
            }
        }
        return myScore-oppScore;
    }
    private Color getOppositeColor(Color color) {
        if(color == Color.WHITE) return Color.BLACK;
        return this.color.WHITE;
    }

    private int getMultiplier(Color color) {
        if(color == Color.WHITE) {
            return -1;
        }
        return 1;
    }
}
