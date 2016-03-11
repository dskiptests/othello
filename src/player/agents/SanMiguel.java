package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Player;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by GarliC on 16-03-11.
 */
public class SanMiguel extends Player{

    private Random random;
    private int boardSize;
    private int squareWeights[][] = {
        {99, -4, 4, 4,4,4,-4,99},
        {-4,-6,-3,-2,-2,-2,-6,-4},
        { 4, -4,-3, 4,1,1,-3, 4},
        { 4, -3,-2, 0,0,1,-2, 4},
        { 4, -3,-2, 0,0,1,-2, 4},
        { 4, -4,-3, 1,1,1,-3, 4},
        {-4,-6,-4,-2,-2,-3,-6,-4},
        {99, -4, 4, 4,4,4,-4, 8}
    };

    public SanMiguel(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        this.random = new Random();
        this.boardSize = 8;
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        int currentScore = board.getNumberOfDisksInColor(COLOR);
        GameBoard boardBackUp = board.copyBoard();

        int highestScore = 0;
        Position bestPosition = currentLegalPositions.get(0);

        for(Position currentPosition : currentLegalPositions){
            board.placeDisk(COLOR, currentPosition);
            if((board.getNumberOfDisksInColor(COLOR)-currentScore)*squareWeights[currentPosition.row][currentPosition.column] > highestScore){
                highestScore = (board.getNumberOfDisksInColor(COLOR)-currentScore)*squareWeights[currentPosition.row][currentPosition.column];
                bestPosition = currentPosition;
            }
            board = boardBackUp;
        }
        return bestPosition;
    }

}
