package game;


import gameschedule.Match;
import player.Player;

import java.util.LinkedList;

public class Game {


    private GameBoard gameBoard;
    private PlayerHandler playerHandler;
    private final long TIMESLOT = 2000;
    private boolean result;


    public Game(Player whitePlayer, Player blackPlayer) {
        newGame(TIMESLOT, blackPlayer, whitePlayer);

    }

    public Player getNextTurn() {
        return this.playerHandler.getCurrentPlayerForGUI();
    }

    public LinkedList<Position> getSlotsToColor() {
        return this.gameBoard.getAllLegalPositions(playerHandler.getCurrentPlayerForGUI().COLOR);
    }

    public Position nextTurn() {

        playerHandler.turn();
        LinkedList<Position> legalMovesForCurrentPlayer = this.gameBoard.getAllLegalPositions(playerHandler.getCurrentPlayer().COLOR);
        if(legalMovesForCurrentPlayer.size() < 1 ) return null;

        Position position = playerHandler.getNextPlayerMove(gameBoard, legalMovesForCurrentPlayer);

        return position;

    }

    public void newGame(long timeslot, Player player1, Player player2) {
        this.playerHandler = new PlayerHandler(player1, player2, timeslot);
        playerHandler.restart();
        this.gameBoard = new GameBoard();

    }


    public boolean isLegalMove(Player player, Position newPosition) {
        return gameBoard.isLegalMove(player.COLOR, newPosition);
    }


    public boolean isLegal(Position position) {
        return gameBoard.isLegalMove(playerHandler.getCurrentPlayerForGUI().COLOR, position);
    }


    public boolean isFinished() {
        return this.gameBoard.gameIsFinished();
    }


    public void flip(Position position) {
        Player player = playerHandler.getCurrentPlayer();
        if(isLegalMove(player, position)) {
            this.gameBoard.placeDisk(player.COLOR,position);
        }
    }



    public Color colorOfPosition(Position position) {
        return gameBoard.getPositionColor(position);
    }


    public Player getPlayerByColor(Color color) {
        return playerHandler.getPlayerByColor(color);
    }

    public int getPlayerScore(Player player) {
        return gameBoard.getNumberOfDisksInColor(player.COLOR);
    }

    public String getKey(Match match) {
        return match.whitePlayer + match.blackPlayer;
    }
}
