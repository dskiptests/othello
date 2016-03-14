package player.agents;
import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;

/**
 * Created by martinpettersson on 18/12/15.
 */
public class Skumtomtarna extends Agent {
    private Color ourColor;
    private Color enemyColor;

    private final int MAX_VAL = 10000000;
    private final int MIN_VAL = -10000000;

    public Skumtomtarna(Color color) {
        super(color);
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
        RetValue ret = negamax(currentBoard,5, ourColor);
        return ret.pos;
    }

    private RetValue negamax(GameBoard board, int depth, Color color) {

        if (depth == 0 || board.gameIsFinished()) {
            //System.err.println(utility(board, myColor));
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
        if (color.equals(ourColor))
            colorSign = 1;

        double numOur = 0;
        double numEnemy = 0;
        Color[][] bordMatrix = board.getBoardMatrix();
        for (int rows = 0; rows < bordMatrix.length; rows++) {
            for (int cols = 0; cols < bordMatrix[0].length; cols++) {
                if (bordMatrix[rows][cols].equals(ourColor)) {

                    numOur++;
                }

                if (bordMatrix[rows][cols].equals(enemyColor))
                    numEnemy++;

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
}
