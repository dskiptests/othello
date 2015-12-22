package player.agents;

import game.Position;
import player.Player;

import java.util.Random;


public class EdgeEddie extends Player {

    private Random random;
    private int boardSize;

    public EdgeEddie(game.COLOR color) {
        super(color);
    }


    @Override
    public void newGame() {
        this.random = new Random();
        System.out.println(NAME + " is alive! :)");
        this.boardSize = 8;
    }


    private boolean coordinateIsACorner(Position position) {
        int row = position.row % (this.boardSize - 1);
        int col = position.column % (this.boardSize - 1);

        if(row == 0 && col == 0) return true;

        return false;
    }


    @Override
    public Position nextMove() {
        int randomIndex = random.nextInt(availablePositions.size());
        Position choosenPosition = this.availablePositions.get(randomIndex);

        for(Position m : this.availablePositions) {
            if(coordinateIsACorner(m)) {
                choosenPosition = m;
            }
        }

        for(Position p : this.availablePositions) {
            if(coordinateIsOnTheEdge(p)) {
                choosenPosition = p;
            }
        }


        return new Position(choosenPosition);
    }

    private boolean coordinateIsOnTheEdge(Position position) {
        int row = position.row % (this.boardSize - 1);
        int col = position.column % (this.boardSize - 1);

        if(row == 0 || col == 0) return true;

        return false;
    }
}
