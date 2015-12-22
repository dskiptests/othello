package player.agents;
import game.COLOR;
import game.Position;
import player.Board;
import player.Player;

import java.util.LinkedList;

/**
 * Created by martinpettersson on 18/12/15.
 */
public class Skumtomtarna extends Player {
    private COLOR ourColor;
    private COLOR enemyColor;

    private final int MAX_VAL = 10000000;
    private final int MIN_VAL = -10000000;

    public Skumtomtarna(game.COLOR color) {
        super(color);
    }

    public void newGame(){
        ourColor = COLOR;
        if (COLOR.equals(COLOR.BLACK)) {
            enemyColor = COLOR.WHITE;
        } else {
            enemyColor = COLOR.BLACK;
        }
    }

    public Position nextMove() throws InterruptedException {
        RetValue ret = negamax(currentBoard,5, ourColor);
        return ret.pos;
    }

    private RetValue negamax(Board board, int depth, COLOR color) {

        if (depth == 0 || board.gameIsFinished()) {
            //System.err.println(utility(board, color));
            return new RetValue(utility(board, color), null);
        }

        double bestValue = -10000000;
        Position bestPos = null;
        LinkedList<Position> childNodes = board.getAllLegalMoves(color);
        for (Position childNode : childNodes) {
            Board childBoard = board.copy();
            childBoard.placeDisk(color, childNode);

            if (color.equals(COLOR.BLACK))
                color = COLOR.WHITE;
            else
                color = COLOR.BLACK;

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

    private double utility(Board board, COLOR color) {

        //remember to do something with color

        double colorSign = -1;
        if (color.equals(ourColor))
            colorSign = 1;

        double numOur = 0;
        double numEnemy = 0;
        COLOR[][] bordMatrix = board.getColorMatrix();
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
