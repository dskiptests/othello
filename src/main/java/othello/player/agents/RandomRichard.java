package othello.player.agents;
import othello.game.Color;
import othello.game.GameBoard;
import othello.game.Position;
import othello.player.Agent;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomRichard extends Agent {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomRichard.class);

    Random random;

    public RandomRichard(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        this.random = new Random();
    }

    @Override
    public Position nextMove(final GameBoard currentBoard, final List<Position> currentLegalPositions) {

        Position position = this.callForHelp("");
        String name = this.name();
        final Color color = currentBoard.colorOf(Position.create(5, 5));
        final int randomIndex = random.nextInt(currentLegalPositions.size());
        return Position.create(this.currentLegalPositions.get(randomIndex));

    }
}

