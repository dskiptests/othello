package player.agents;

import game.*;
import player.Player;

import java.util.LinkedList;


public class StableDiskSally extends Player {

    private int[][] scoreMatrix;


    public StableDiskSally(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        scoreMatrix = new int[][]
                {{  40,     1,      15,     5},
                {   1,      0,      3,      3},
                {   15,     3,      5,      3},
                {   5,      3,      3,      3}};
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        int maxScore = Integer.MIN_VALUE;
        Position returnPosition = null;

        for(Position pos : this.currentLegalPositions) {
            int naiveRank = getNaiveScoreForPosition(pos);
            int neighbourRank = getNeighbourScoreForPosition(pos);
            int score = (naiveRank+ neighbourRank);

            System.out.println(pos + " " + getNaiveScoreForPosition(pos) + " " + getNeighbourScoreForPosition(pos));

            if(score > maxScore) {
                maxScore = score;
                returnPosition = pos;
            }
        }

        return returnPosition;
    }

    private int getNeighbourScoreForPosition(Position position) {
        position = convertPosition(position);
        int x = position.row;
        int y = position.column;
        Color[][] m = this.currentBoard.copyMatrix();

        if(x > 3) x = 4;
        else x = 0;
        if(y > 3) y = 4;
        else y = 0;

        int score = 0;
        for(int i = x; i < x+4; i++) {
            for(int j = y; j < y+4; j++) {
                if(m[i][j] == this.COLOR) score++;
            }
        }

        return score;
    }


    private int getNaiveScoreForPosition(Position position) {
        position = convertPosition(position);
        return scoreMatrix[position.row][position.column];
    }

    private Position convertPosition(Position position) {
        int x = position.row;
        int y = position.column;

        int size = scoreMatrix.length;
        if(x > size - 1) x = (size-1) - (x % size);
        if(y > size- 1) y = (size-1) - (y % size);

        return new Position(x,y);
    }






}
