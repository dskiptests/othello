package player.impl;

import game.Move;
import player.*;

import java.util.Random;


public class TerribleTerry extends Player {


    Random random;


    public TerribleTerry(game.COLOR color) {
        super(color);
    }

    @Override
    public void initialize() {
        System.out.println(NAME + " is alive! :)");
        this.random = new Random();
    }

    private boolean coordinateIsATerribleMove(Move move) {
        int boardSize = 8;
        int row = (move.row - 1) % (boardSize - 3);
        int col = (move.column - 1) % (boardSize - 3);

        if(row == 0 && col == 0) return true;

        return false;
    }

    @Override
    public Move nextMove() {

        for(Move m : this.availableMoves) {
            if(coordinateIsATerribleMove(m)) {
                return m;
            }
        }

        return this.availableMoves.get(random.nextInt(availableMoves.size()));
    }
}

