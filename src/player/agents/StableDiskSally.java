package player.agents;

import game.Position;
import player.Player;


public class StableDiskSally extends Player {

    private int[][] scoreMatrix;


    public StableDiskSally(game.COLOR color) {
        super(color);
    }

    @Override
    public void newGame() {
        scoreMatrix = new int[][]   {{  30, -25, 10, 10},
                                    {  -25, -25, 7,  1},
                                    {   10,  7,  5,  1},
                                    {   10,  1,  1,  1}};
    }

    @Override
    public Position nextMove() throws InterruptedException {

        int maxScore = Integer.MIN_VALUE;
        Position returnPosition = null;

        for(Position p : this.currentLegalPositions) {
            System.out.println(p + " " + getScoreForPosition(p));
            if(getScoreForPosition(p) > maxScore) {

                maxScore = getScoreForPosition(p);
                returnPosition = p;
            }
        }
        System.out.println("Returning " + returnPosition + " " + getScoreForPosition(returnPosition));
        return returnPosition;
    }


    private int getScoreForPosition(Position position) {
        int x = position.row;
        int y = position.column;

        int size = scoreMatrix.length;
        if(x > size - 1) x = (size-1) - (x % size);
        if(y > size- 1) y = (size-1) - (y % size);

       // System.out.println(position.row + " --> " + x + ", " + position.column + " --> " + y);
        return scoreMatrix[x][y];
    }




}
