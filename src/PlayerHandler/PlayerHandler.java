package PlayerHandler;


import game.Move;
import gui.GameBoard;
import player.Player;
import player.impl.SimplePlayer;

import java.util.concurrent.*;

public class PlayerHandler {

//    final Player[] players;






    public void test(Player player) throws InterruptedException, ExecutionException {




       // Player player = new SimplePlayer();
        ExecutorService service = Executors.newFixedThreadPool(1);



        Future<Move> futureResult = service.submit(player);
        Move result = null;
        long start = System.currentTimeMillis();
        try{
            result = futureResult.get(2000, TimeUnit.MILLISECONDS);
        }catch(TimeoutException e){
            System.out.println("No response after one second");
            futureResult.cancel(true);
        }
        service.shutdown();



    }




}
