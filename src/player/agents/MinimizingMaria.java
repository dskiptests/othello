package player.agents;

import game.GameBoard;
import game.Position;
import player.Agent;
import game.Color;

import java.util.LinkedList;
import java.util.Random;


public class MinimizingMaria extends Agent {


    private Random random;
    private int boardSize;

    public MinimizingMaria(Color color) {
        super(color);
    }


    @Override
    public void newGame() {
        this.random = new Random();
        System.out.println(NAME + " is alive! :)");
        this.boardSize = 8;
    }

    @Override
    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        int randomIndex = random.nextInt(currentLegalPositions.size());
        Position choosenPosition = this.currentLegalPositions.get(randomIndex);
        Color oppositeColor = getOppositeColor();

        int maxNumberOfMoves = Integer.MAX_VALUE;
        for(Position p : this.currentLegalPositions) {

            GameBoard tempBoard = this.currentBoard.copyBoard();
            tempBoard.placeDisk(this.color, p);
            int numberOfMovesForOpponent = tempBoard.getAllLegalPositions(oppositeColor).size();

            if(numberOfMovesForOpponent < maxNumberOfMoves) {
                choosenPosition = new Position(p);
            }
        }


        return choosenPosition;
    }

    private Color getOppositeColor() {
        if(this.color == Color.WHITE) return Color.BLACK;
        return color.WHITE;
    }
}
