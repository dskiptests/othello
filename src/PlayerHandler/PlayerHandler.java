package PlayerHandler;


import game.Move;
import gui.GameBoard;
import player.Player;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlayerHandler {

//    final Player[] players;

    public PlayerHandler(Player player1, Player player2) {
//        players = new Player[]{player1, player2};

        try {
            test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public PlayerHandler() {

        try {
            test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Move getMoveFromPlayer(GameBoard board) {



        return new Move(1,1);
    }


    private void test() throws InterruptedException, ExecutionException {




        Callable player = new Player();


        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);


        ScheduledFuture sf = scheduledPool.schedule(player, 1l, TimeUnit.SECONDS);

        String value = (String) sf.get();

        System.out.println("Callable returned"+value);

        scheduledPool.shutdownNow();

        System.out.println("Is ScheduledThreadPool shutting down? "+scheduledPool.isShutdown());
    }




}
