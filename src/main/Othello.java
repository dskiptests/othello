package main;
import gui.*;

public class Othello {


    private final GUIWindow GUIWindow;
    private Game currentGame;

    public Othello() {
        this.GUIWindow = new GUIWindow();
    }






    public void newGame() {
        this.currentGame = new Game();
    }

    public static void main(String[] args) {
        new Othello();
    }
}
