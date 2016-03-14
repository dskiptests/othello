package game;


import gameschedule.Match;
import player.Agent;

import java.util.LinkedList;

public class Game {


    private GameBoard gameBoard;
    private PlayerHandler playerHandler;
    private final long TIMESLOT = 2000;
    private boolean result;


    public Game(Agent whiteAgent, Agent blackAgent) {
        this.gameBoard = new GameBoard();
        newGame(TIMESLOT, blackAgent, whiteAgent);

    }

    public Agent getNextTurn() {
        return this.playerHandler.getCurrentPlayerForGUI();
    }

    public LinkedList<Position> getSlotsToColor() {
        return this.gameBoard.getAllLegalPositions(playerHandler.getCurrentPlayerForGUI().color);
    }

    public Position nextTurn() {

        playerHandler.turn();
        LinkedList<Position> legalMovesForCurrentPlayer = this.gameBoard.getAllLegalPositions(playerHandler.getCurrentPlayer().color);
        if(legalMovesForCurrentPlayer.size() < 1 ) return null;

        Position position = playerHandler.getNextPlayerMove(gameBoard, legalMovesForCurrentPlayer);

        return position;

    }

    public void newGame(long timeslot, Agent agent1, Agent agent2) {
        this.playerHandler = new PlayerHandler(agent1, agent2, timeslot);
        playerHandler.restart();


    }


    public boolean isLegalMove(Agent agent, Position newPosition) {
        return gameBoard.isLegalMove(agent.color, newPosition);
    }


    public boolean isLegal(Position position) {
        return gameBoard.isLegalMove(playerHandler.getCurrentPlayerForGUI().color, position);
    }


    public boolean isFinished() {
        return this.gameBoard.gameIsFinished();
    }


    public void flip(Position position) {
        Agent agent = playerHandler.getCurrentPlayer();
        if(isLegalMove(agent, position)) {
            this.gameBoard.placeDisk(agent.color,position);
        }
    }

    public Color colorOfPosition(Position position) {
        return gameBoard.getPositionColor(position);
    }

    public Agent getPlayerByColor(Color color) {
        return playerHandler.getPlayerByColor(color);
    }

    public int getPlayerScore(Agent agent) {
        return gameBoard.getNumberOfDisksInColor(agent.color);
    }

    public String getKey(Match match) {
        return match.whitePlayer + match.blackPlayer;
    }
}
