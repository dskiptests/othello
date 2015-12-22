package player.agents;

import game.Position;
import player.Player;
import game.COLOR;
import player.Board;

import java.util.Random;


public class MinimizingMaria extends Player {


    private Random random;
    private int boardSize;

    public MinimizingMaria(game.COLOR color) {
        super(color);
    }


    @Override
    public void newGame() {
        this.random = new Random();
        System.out.println(NAME + " is alive! :)");
        this.boardSize = 8;
    }

    @Override
    public Position nextMove() {

        int randomIndex = random.nextInt(availablePositions.size());
        Position choosenPosition = this.availablePositions.get(randomIndex);
        COLOR oppositeColor = getOppositeColor();

        int maxNumberOfMoves = Integer.MAX_VALUE;
        for(Position p : this.availablePositions) {

            Board tempBoard = this.currentBoard.copy();
            tempBoard.placeDisk(this.COLOR, p);
            int numberOfMovesForOpponent = tempBoard.getAllLegalMoves(oppositeColor).size();

            if(numberOfMovesForOpponent < maxNumberOfMoves) {
                choosenPosition = new Position(p);
            }
        }


        return choosenPosition;
    }

    private COLOR getOppositeColor() {
        if(this.COLOR == game.COLOR.WHITE) return game.COLOR.BLACK;
        return COLOR.WHITE;
    }
}
