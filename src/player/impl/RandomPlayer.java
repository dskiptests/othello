package player.impl;

import game.Move;
import player.*;

/**
 * Created by david on 09/11/15.
 */
public class RandomPlayer extends Player {


    @Override
    public void initialize() {

    }

    @Override
    public Move nextMove() {
        System.out.print("Random player is playing!!");
        return null;
    }
}
