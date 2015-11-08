package player;


import java.util.concurrent.Callable;

public class Player implements Callable {


    @Override
    public String call() throws Exception {
        while(true) {
            System.out.println("This is a call from Player!");
            if("".length() == -1) break;
        }

        return "player-player";
    }
}
