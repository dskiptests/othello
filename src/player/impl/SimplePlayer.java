package player.impl;

import game.Move;
import player.Player;



public class SimplePlayer extends Player {

    @Override
    public void initialize() {
        System.out.println("SimplePlayer initialized!");
    }


    @Override
    public Move nextMove() {
        System.out.println("SimplePlayer move!");
        return this.availableMoves.getFirst();
    }
}
