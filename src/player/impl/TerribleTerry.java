package player.impl;

import game.Position;
import player.*;

import java.util.Random;


public class TerribleTerry extends Player {


    Random random;


    public TerribleTerry(game.COLOR color) {
        super(color);
    }

    @Override
    public void newGame() {
        System.out.println(NAME + " is alive! :)");
        this.random = new Random();
    }

    private boolean coordinateIsATerribleMove(Position position) {
        int boardSize = 8;
        int row = (position.row - 1) % (boardSize - 3);
        int col = (position.column - 1) % (boardSize - 3);

        if(row == 0 && col == 0) return true;

        return false;
    }

    @Override
    public Position nextMove() {

        for(Position m : this.availablePositions) {
            if(coordinateIsATerribleMove(m)) {
                return m;
            }
        }

        return new Position(this.availablePositions.get(random.nextInt(availablePositions.size())));
    }
}

