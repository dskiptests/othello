package player.agents;

import game.GameBoard;
import game.Position;
import player.Player;
import game.COLOR;

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

        int randomIndex = random.nextInt(currentLegalPositions.size());
        Position choosenPosition = this.currentLegalPositions.get(randomIndex);
        COLOR oppositeColor = getOppositeColor();

        int maxNumberOfMoves = Integer.MAX_VALUE;
        for(Position p : this.currentLegalPositions) {

            GameBoard tempBoard = this.currentBoard.copyBoard();
            tempBoard.placeDisk(this.COLOR, p);
            int numberOfMovesForOpponent = tempBoard.getAllLegalPositions(oppositeColor).size();

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
