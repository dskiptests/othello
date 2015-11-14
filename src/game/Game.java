package game;


import player.Player;

import player.impl.SimpleSimon;
import player.impl.RandomRichard;

import java.util.LinkedList;

public class Game {


    public Board board;
    private PlayerHandler playerHandler;
    private boolean finished;

    public Game() {

        newGame(2000);
    }





    public Move next() {
        System.out.println(".");


        Player currentPlayer = playerHandler.getCurrentPlayer();
        System.out.println("CURRENT PLAYER: " + currentPlayer.getColor() + ", NAME: " + currentPlayer.NAME + " " + playerHandler.turn);

        this.board.currentPlayer = currentPlayer;
        LinkedList<Move> legalMovesForCurrentPlayer = this.board.getAllLegalMoves();
        System.out.println("Legal moves returned: " + legalMovesForCurrentPlayer.size());
        if(legalMovesForCurrentPlayer.size() < 1 ) return null;



        Move move = playerHandler.getNextPlayerMove(board, legalMovesForCurrentPlayer);

        return move;

    }

    public void newGame(long timeslot) {
        Player player1 = new SimpleSimon(COLOR.WHITE);
        Player player2 = new RandomRichard(COLOR.BLACK);
        this.playerHandler = new PlayerHandler(player1, player2, timeslot);
        playerHandler.restart();
        this.board = new Board(playerHandler.getCurrentPlayer());

    }


    public boolean isLegalMove(Move move) {
        return board.doFlip(move, false);
    }

    public boolean isFinished() {
        return 0 == this.board.chkWinner();
    }

    public void switchPlayer() {
       playerHandler.turn = (playerHandler.turn + 1) % 2;
    }
}
