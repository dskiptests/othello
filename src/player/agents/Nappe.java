package player.agents;
import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Nappe on 11/03/16.
 */
public class Nappe extends Agent {
    private Color ourColor;
    private Color enemyColor;

    long start = 0;
    long end = 0;

    int boardSize;

    private final int MAX_VAL = 10000000;
    private final int MIN_VAL = -10000000;

    public Nappe(Color color) {
        super(color);
        this.boardSize = 8;
    }

    public void newGame(){
        ourColor = color;
        if (color.equals(color.BLACK)) {
            enemyColor = color.WHITE;
        } else {
            enemyColor = color.BLACK;
        }
    }

    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        ListIterator<Position> it = currentLegalPositions.listIterator();

        start = System.currentTimeMillis();

        while (it.hasNext()) {
            Position pos = it.next();
            if (coordinateIsACorner(pos.row, pos.column)) {
                return pos;
            }
        }

        RetValue ret = negamax(currentBoard,6, ourColor);
        return ret.pos;
    }

    private RetValue negamax(GameBoard board, int depth, Color color) {

        if (depth == 0 || board.gameIsFinished()) {
            return new RetValue(utility(board, color), null);
        }

        end = System.currentTimeMillis() - start;
        if (end > 1600) {
            return new RetValue(utility(board, color), null);
        }
        double bestValue = -10000000;
        Position bestPos = null;
        LinkedList<Position> childNodes = board.getAllLegalPositions(color);
        for (Position childNode : childNodes) {
            GameBoard childBoard = board.copyBoard();
            childBoard.placeDisk(color, childNode);

            if (color.equals(this.color.BLACK))
                color = this.color.WHITE;
            else
                color = this.color.BLACK;

            RetValue retval = negamax(childBoard, depth-1, color);
            double val = -retval.value;
            if (val > bestValue) {
                bestValue = val;
                bestPos = retval.pos;
            }


        }



        RetValue ret = new RetValue(bestValue, bestPos);
        return ret;
    }

    private class RetValue {
        public double value;
        public Position pos;
        public RetValue(double val, Position pos) {
            this.value = val;
            this.pos = pos;
        }
    }

    private double utility(GameBoard board, Color color) {

        //remember to do something with myColor

        double colorSign = -1;
        if (color.equals(ourColor)) {
            colorSign = 1;
        }

        double numOur = 0;
        double numEnemy = 0;
        Color[][] bordMatrix = board.getBoardMatrix();
        for (int rows = 0; rows < bordMatrix.length; rows++) {
            for (int cols = 0; cols < bordMatrix[0].length; cols++) {

                if (bordMatrix[rows][cols].equals(ourColor)) {
                    if (coordinateIsACorner(rows, cols)) {
                        numOur += 1000;
                    } else if (coordinateIsOnTheEdge(rows, cols)) {
                        numOur += 100;
                    }
                    numOur++;
                }

                if (!bordMatrix[rows][cols].equals(enemyColor)) {
                    if (coordinateIsACorner(rows, cols)) {
                        numEnemy += 1000;
                    } else if (coordinateIsOnTheEdge(rows, cols)) {
                        numEnemy += 100;
                    }
                    numEnemy++;
                }

            }

        }
        // System.err.println("Num our: " + numOur);
        double quote =  (numOur / numEnemy) * 10;

        if (board.gameIsFinished() && numOur > numEnemy) {
            quote += 100000;

        }

        else if (board.gameIsFinished() && numOur < numEnemy)
            quote -= 100000;

        // System.err.println(colorSign*quote);
        return colorSign * quote;

    }

    private boolean coordinateIsACorner(int row, int col) {
        row = row % (this.boardSize - 1);
        col = col % (this.boardSize - 1);

        if(row == 0 && col == 0) return true;

        return false;
    }

    private boolean coordinateIsOnTheEdge(int row, int col) {
        row = row % (this.boardSize - 1);
        col = col % (this.boardSize - 1);

        if(row == 0 || col == 0) return true;
        return false;
    }

}
