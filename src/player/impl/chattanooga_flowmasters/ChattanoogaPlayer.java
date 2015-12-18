package player.impl.chattanooga_flowmasters;

import game.COLOR;
import game.Position;
import player.Board;
import player.Player;

import java.util.*;

public class ChattanoogaPlayer extends Player {

    private final Comparator<ImmutablePair<Position, Integer>> immutablePairComparator = (ip1, ip2) -> ip1.getRight().compareTo(ip2.getRight());

    public ChattanoogaPlayer(COLOR color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove() throws InterruptedException {

        List<ImmutablePair<Position, Integer>> positionScores = new ArrayList<>(availablePositions.size() + 1);

        for (Position availablePosition : availablePositions) {
            positionScores.add(new ImmutablePair<>(availablePosition, scorePosition(availablePosition, currentBoard)));
        }

        Collections.sort(positionScores, immutablePairComparator);

        return positionScores.get(positionScores.size() -1).getLeft();
    }

    private int scorePosition(final Position position, final Board board) {
        return 0;
    }


    private class ImmutablePair<LEFT, RIGHT> {
        private final LEFT left;
        private final RIGHT right;


        private ImmutablePair(LEFT left, RIGHT right) {
            this.left = left;
            this.right = right;
        }


        LEFT getLeft() {
            return left;
        }

        RIGHT getRight() {
            return right;
        }
    }


}
