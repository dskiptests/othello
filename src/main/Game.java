package main;

import gui.GameBoard;

/**
 * Created by david on 06/11/15.
 */
public class Game {



    private GameBoard gameBoard;

    public Game() {
        this.gameBoard = new GameBoard();
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }



}
