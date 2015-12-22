package player.agents;
import game.COLOR;
import game.Position;
import player.Board;
import player.Player;

import java.util.LinkedList;
import java.util.Random;


/**
 * Created by DeepOthello on 18/12/2015.
 */
public class DeepOthello extends Player{
    public DeepOthello(game.COLOR color) {
        super(color);
    }



    private Random random;
    private final int BOARD_SIZE = 8;
    private int[][] t;
    int turnsPlayed = 0;



    @Override
    public void newGame() {

        t = new int[BOARD_SIZE][BOARD_SIZE];
        random = new Random();

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
    public Position nextMove() throws InterruptedException {

        COLOR oppositeColor = getOppositeColor();

        Position worstEnemyPosition = null;
        int enemyMinMax = Integer.MAX_VALUE;

        int maxDiff = Integer.MIN_VALUE;


        for(Position p : this.availablePositions) {

            Board tempBoard = this.currentBoard.copy();
            int ourCurrentValue = t[p.column][p.row];
            //int ourBoardValue = t[p.column][p.row];

            tempBoard.placeDisk(this.COLOR, p);

            int nOurColorBefore = 0;
            int nOurColorAfter = 0;
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++) {
                    if(this.currentBoard.getColorMatrix()[i][j] == this.COLOR) nOurColorBefore++;
                    if(tempBoard.getColorMatrix()[i][j] == this.COLOR) nOurColorAfter++;
                }
            }
            int diff = nOurColorAfter-nOurColorBefore;

            if (diff > maxDiff) {
                maxDiff = diff;
                worstEnemyPosition = p;
            }


            //int enemyMax = calculateMaxValue(tempBoard.getAllLegalMoves(oppositeColor));
            int enemyMax = calculateBoardValue(tempBoard.getAllLegalMoves(oppositeColor));

            if (enemyMax < enemyMinMax) {
                enemyMinMax = enemyMax;
                worstEnemyPosition = p;
            }

            /*
            int maxEnemyValue = -1000;
            //for (int h = 0; h < numberOfMovesForOpponent ; h++){
            int tempEnemyValue = calculateMaxValue(tempBoard.getAllLegalMoves(oppositeColor));
            //int tempEnemyValue = calculateBoardValue(tempBoard.getAllLegalMoves(oppositeColor));
            if(tempEnemyValue > maxEnemyValue) {
                maxEnemyValue = tempEnemyValue;
            }
            //}

            if (maxEnemyValue < minEnemyValue) {
                minEnemyValue = maxEnemyValue;
                worstEnemyPosition = p;
            }
            */
        }

        //turnsPlayed++;
        return worstEnemyPosition;
    }

    private int calculateMaxValue(LinkedList<game.Position> availiblePositions){

        int bestValue = 0;
        Position bestPosition = null;
        for (int i = 0; i < availablePositions.size(); i++){
            Position choosenPosition = availablePositions.get(i);

            int value = t[choosenPosition.column][choosenPosition.row];
            if(value > bestValue){
                bestValue= value;
                bestPosition = choosenPosition;
            }
        }
        return bestValue;
    }

    enum scoreFunction {
        MAXVALUE,
        BOARDVALUE
    }

    private int calculateBoardValue(LinkedList<game.Position> availablePositions){

        int sumValue = 0;

        for (int i = 0; i < availablePositions.size(); i++){
            Position choosenPosition = availablePositions.get(i);

            sumValue += t[choosenPosition.column][choosenPosition.row];

        }
        return sumValue;
    }




    private COLOR getOppositeColor() {
        if(this.COLOR == game.COLOR.WHITE) return game.COLOR.BLACK;
        return COLOR.WHITE;
    }
}
