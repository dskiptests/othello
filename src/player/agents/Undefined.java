package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;
import java.util.List;

public class Undefined extends Agent {
    private static final int MAX_DEPTH = 4;
    private static final int[][] BOARD_VALUES =
            {{20, -3, 11, 8, 8, 11, -3, 20},
            {-3, -7, -4, 1, 1, -4, -7, -3},
            {11, -4,  2, 2, 2,  2, -4, 11},
            { 8,  1,  2,-3,-3,  2,  1,  8},
            { 8,  1,  2,-3,-3,  2,  1,  8},
            {11, -4,  2, 2, 2,  2, -4, 11},
            {-3, -7, -4, 1, 1, -4, -7, -3},
            {20, -3, 11, 8, 8, 11, -3, 20}};

    public Undefined(Color color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        int maxScore = Integer.MIN_VALUE;
        Position bestPos = currentLegalPositions.get(0);
        for (Position pos : currentLegalPositions) {
            int score = minmax(true, this.color, board.copyBoard(), currentLegalPositions, 0);
            if (score > maxScore) {
                maxScore = score;
                bestPos = pos;
            }
        }
        return bestPos;
    }

    private static int minmax(boolean maximizing, Color playerColor, GameBoard board,
                                   List<Position> currentLegalPositions, int depth) {
        int bestScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        Color opponentColor = flipColor(playerColor);
        for (Position pos : currentLegalPositions) {
            GameBoard copyBoard = board.copyBoard();
            // Try placing the disk and recursively check what the opponent could do.
            copyBoard.placeDisk(opponentColor, pos);


            int score;
            if (depth == MAX_DEPTH) {
                score = evaluateBoard(board, playerColor);
            } else {
                score = minmax(!maximizing, opponentColor, copyBoard,
                            copyBoard.getAllLegalPositions(opponentColor), depth + 1);
            }
            if ((maximizing && score > bestScore) || (!maximizing && score < bestScore)) {
                bestScore = score;
            }
        }

        return bestScore;
    }

    private static Color flipColor(Color color) {
        return color == Color.BLACK ? Color.WHITE : Color.BLACK;
    }

    private static int evaluateBoard(GameBoard board, Color playerColor) {
        int sum = 0;
        Color[][] boardMatrix = board.getBoardMatrix();

        for (int row = 0; row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[0].length; col++) {
                if (boardMatrix[row][col] == playerColor) {
                    sum += BOARD_VALUES[row][col];
                } else {
                    sum -= BOARD_VALUES[row][col];
                }
            }
        }

        return sum;
    }
}
