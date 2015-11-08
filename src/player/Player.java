package player;


import game.Game;
import gui.GameBoard;

import java.util.Random;
import java.util.concurrent.Callable;

public class Player implements Callable<String> {


    GameBoard board;

    @Override
    public String call() throws Exception {
        System.out.println("Ny");
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        try{
            for(int i=0;i<Integer.MAX_VALUE;++i){
                builder.append("a");
                Thread.sleep(1);
            }
        }catch(Exception e){
            return "arne";

        }
        return "mega";
    }


    public void arne(GameBoard board) {
        System.out.println("Arne M");
        this.board = board;
    }
}
