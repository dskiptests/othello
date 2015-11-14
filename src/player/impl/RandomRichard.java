package player.impl;

import game.COLOR;
import game.Move;
import player.*;

import java.util.Random;


public class RandomRichard extends Player {


    Random random;

    public RandomRichard(game.COLOR color) {
        super(color);
    }

    @Override
    public void initialize() {
        System.out.println(NAME + " is alive! :)");
        this.random = new Random();
    }

    @Override
    public Move nextMove() {
        int randomIndex = random.nextInt(availableMoves.size());
        Move randomMove = this.availableMoves.get(randomIndex);
        return randomMove;
    }
}

