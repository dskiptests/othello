package othello.player.agents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;
import othello.player.Agent;

import java.util.List;
import java.util.Random;

public class RandomRichard2 extends Agent {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomRichard2.class);

    Random random;

    public RandomRichard2(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        this.random = new Random();
    }

    @Override
    public Position nextMove(final GameBoard currentBoard, final List<Position> currentLegalPositions) {

        Position position = this.callForHelp("");

        final Color color = currentBoard.colorOf(Position.create(5, 5));
        final int randomIndex = random.nextInt(currentLegalPositions.size());
        return Position.create(this.currentLegalPositions.get(randomIndex));

    }
}

