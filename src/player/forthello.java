/**
 * Created by ericleijonmarck on 2015-12-18.
 */
package player.impl;
import game.Position;
import player.Player;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class forthello extends Player {

    Random random;

    public forthello(game.COLOR color) {
        super(color);
    }

    @Override
    public void newGame() {
        System.out.println(NAME + " is alive! :) " + this.COLOR);
        this.random = new Random();
    }

    @Override
    public Position nextMove() throws InterruptedException {int randomIndex = random.nextInt(availablePositions.size());
        return new Position(this.availablePositions.get(randomIndex));
    }
}
