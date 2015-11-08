package player;

import game.Move;

import java.util.LinkedList;

/**
 * Created by david on 2015-11-08.
 */
public interface Strategy {

    public Move getNewMove(LinkedList<Move> moves);


}
