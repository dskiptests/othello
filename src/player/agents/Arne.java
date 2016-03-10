package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Player;

import java.util.LinkedList;


public class Arne extends Player {

    public Arne(Color color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        return null;
    }
}
