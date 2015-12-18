package game;


import player.Player;

import java.util.LinkedList;

public class Game {


    private GameBoard gameBoard;
    private PlayerHandler playerHandler;
    private boolean finished;




    public Game(Player whitePlayer, Player blackPlayer) {
        newGame(2000, blackPlayer, whitePlayer);

    }

    public Player getNextTurn() {
        return this.playerHandler.getCurrentPlayerForGUI();
    }

    public Player getNextPlayer() {
        return this.playerHandler.getCurrentPlayer();
    }

    public LinkedList<Position> getAllLegalMoves() {
        return this.gameBoard.getAllLegalMoves(playerHandler.getCurrentPlayer());
    }


    public LinkedList<Position> getSlotsToColor() {
        return this.gameBoard.getAllLegalMoves(playerHandler.getPreviousPlayer());
    }

    public Position next() {
        playerHandler.turn();

        LinkedList<Position> legalMovesForCurrentPlayer = this.gameBoard.getAllLegalMoves(playerHandler.getCurrentPlayer());
        if(legalMovesForCurrentPlayer.size() < 1 ) return null;

        Position position = playerHandler.getNextPlayerMove(gameBoard, legalMovesForCurrentPlayer);

        return position;

    }

    public void newGame(long timeslot, Player player1, Player player2) {
        this.playerHandler = new PlayerHandler(player1, player2, timeslot);
        playerHandler.restart();
        this.gameBoard = new GameBoard(playerHandler.getCurrentPlayer());

    }


    public boolean isLegalMove(Player player, Position position) {
        return gameBoard.isLegalMove(player, position);
    }

    public boolean isLegalMove(Position position) {
        return gameBoard.doFlip(playerHandler.getCurrentPlayer(), position, false);
    }

    public boolean isAvailable(Position position) {
        return gameBoard.doFlip(playerHandler.getCurrentPlayerForGUI(), position, false);
    }


    public boolean isFinished() {
        return 0 == this.gameBoard.chkWinner();
    }

    public void switchPlayer() {

    }

    public boolean flip(Position m) {
        
        Player p = playerHandler.getCurrentPlayer();
        if(isLegalMove(p, m)) {
            this.gameBoard.doFlip(p,m,true);
            this.gameBoard.placeDisk(p,m);
            return true;
        }
        return false;
    }

    public COLOR colorOfPosition(Position position) {
        return gameBoard.getPosition(position);
    }

}
