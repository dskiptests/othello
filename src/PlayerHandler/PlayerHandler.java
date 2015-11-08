package PlayerHandler;


import game.Move;
import gui.GameBoard;
import player.Player;

import java.util.concurrent.*;

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




        Player player = new Player();
        ExecutorService service = Executors.newFixedThreadPool(1);

        player.arne(new GameBoard());

        Future<String> futureResult = service.submit(player);
        String result = null;
        long start = System.currentTimeMillis();
        try{
            result = futureResult.get(2000, TimeUnit.MILLISECONDS);
        }catch(TimeoutException e){
            System.out.println("No response after one second");
            futureResult.cancel(true);
        }
        System.out.println(result);
        service.shutdown();



    }




}
