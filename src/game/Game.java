package game;

import gui.GameBoard;
import player.Player;
import PlayerHandler.PlayerHandler;
import player.impl.RandomPlayer;
import player.impl.SimplePlayer;

import java.util.concurrent.ExecutionException;

/**
 * Created by david on 06/11/15.
 */
public class Game {


    private GameBoard gameBoard;
    private PlayerHandler playerHandler;
    private final Player[] players;

    private int turn;

    public Game() {
        this.players = new Player[2];
        newGame();
    }

    public void next() {
        turn = (turn+1) % 2;

        try {
            playerHandler.test(players[turn]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void newGame() {
        newGame(new SimplePlayer(), new RandomPlayer());
    }

    public void newGame(Player player) {
        newGame(player, new RandomPlayer());
    }

    public void newGame(Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;
        for(Player p : players) p.initialize();

        this.turn = 1;
        this.gameBoard = new GameBoard();
        this.playerHandler = new PlayerHandler();
    }


}
