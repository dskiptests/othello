package othello.player.agents;
import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;
import othello.player.Agent;

import java.util.LinkedList;
import java.util.Random;


public class RandomRichard extends Agent {

    Random random;

    public RandomRichard(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        this.random = new Random();
    }

    @Override
    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        int randomIndex = random.nextInt(currentLegalPositions.size());
        return new Position(this.currentLegalPositions.get(randomIndex));
    }
}

