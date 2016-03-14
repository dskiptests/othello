package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;
import java.util.Random;


public class EdgeEddie extends Agent {

    private Random random;
    private int boardSize;

    public EdgeEddie(Color color) {
        super(color);
    }


    @Override
    public void newGame() {
        this.random = new Random();
        this.boardSize = 8;
    }


    private boolean coordinateIsACorner(Position position) {
        int row = position.row % (this.boardSize - 1);
        int col = position.column % (this.boardSize - 1);

        if(row == 0 && col == 0) return true;

        return false;
    }


    @Override
    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        int randomIndex = random.nextInt(currentLegalPositions.size());
        Position choosenPosition = this.currentLegalPositions.get(randomIndex);

        for(Position m : this.currentLegalPositions) {
            if(coordinateIsACorner(m)) {
                choosenPosition = m;
            }
        }

        for(Position p : this.currentLegalPositions) {
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
