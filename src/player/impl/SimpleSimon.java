package player.impl;

import game.Move;
import player.Player;

import java.util.Random;


public class SimpleSimon extends Player {


    private Random random;
    private int boardSize;

    public SimpleSimon(game.COLOR color) {
        super(color);
    }


    @Override
    public void initialize() {
        this.random = new Random();
        System.out.println(NAME + " is alive! :)");
        this.boardSize = 8;
    }


    private boolean coordinateIsACorner(Move move) {
        int row = move.row % (this.boardSize - 1);
        int col = move.column % (this.boardSize - 1);

        if(row == 0 && col == 0) return true;

        return false;
    }




    @Override
    public Move nextMove() {

        int randomIndex = random.nextInt(availableMoves.size());
        Move choosenMove = this.availableMoves.get(randomIndex);

        for(Move m : this.availableMoves) {
            if(coordinateIsACorner(m)) {
                choosenMove = m;
            }
        }


        return choosenMove;
    }
}
