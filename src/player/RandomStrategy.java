package player;

import game.Move;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by david on 2015-11-08.
 */
public class RandomStrategy implements Strategy {

    private final Random random;

    public RandomStrategy() {
        this.random = new Random();
    }

    @Override
    public Move getNewMove(LinkedList<Move> moves) {
        return moves.get(random.nextInt() % moves.size());
    }
}
