package game;
import player.*;


import java.util.LinkedList;
import java.util.concurrent.*;

public class PlayerHandler {

    private final Player[] players;
    public int turn;
    public final long TIMESLOT;


    public Player getCurrentPlayer() {
        return players[turn];
    }

    public PlayerHandler(Player player1, Player player2, long timeslot) {
        this.TIMESLOT = timeslot;
        this.players = new Player[]{player1, player2};
        this.turn = 0;

        for(Player p : players) p.initialize();

    }

    public Move getNextPlayerMove(Board currentBoard, LinkedList<Move> legalMoves) {
        if(legalMoves.size() < 1) {
            turn = (turn+1) % 2;
            return null;
        }

        Player currentPlayer = players[turn];
        currentPlayer.currentBoard = currentBoard;
        currentPlayer.availableMoves = legalMoves;
        Move playerMove = getMove(currentPlayer);


        turn = (turn+1) % 2;
        if(playerMoveIsLegal(legalMoves, playerMove)) {
            return playerMove;
        }

        return chooseARandomMove(legalMoves);
    }

    private boolean playerMoveIsLegal(LinkedList<Move> legalMoves, Move move) {
        if(move == null) return false;
        if(!legalMoves.contains(move)) return false;
        return true;
    }


    private Move chooseARandomMove(LinkedList<Move> moves) {

        if(moves.size() < 1) return null;
        return moves.getFirst();
    }


    private Move getMove(Player player) {

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Move> futureResult = service.submit(player);
        Move result = null;

        try{
            result = futureResult.get(TIMESLOT, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            System.out.println("No response after one second");
            futureResult.cancel(true);
            e.printStackTrace();
        }
        service.shutdown();


        return result;
    }


    public void restart() {
        turn = 0;
    }
}
