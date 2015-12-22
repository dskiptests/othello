package game;
import player.*;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;

public class PlayerHandler {

    private final Player[] players;
    private int turn;
    public final long TIMESLOT;
    private Map<Player, Long> time;
    private boolean noReturnFromPlayer;


    public Player getCurrentPlayer() {

        return players[turn];
    }


    public Player getCurrentPlayerForGUI() {
        return players[(turn+1) % 2];

    }


    public Player turn() {
        turn = (turn+1) % 2;
        return players[turn];
    }

    public PlayerHandler(Player player1, Player player2, long timeslot) {
        this.TIMESLOT = timeslot;
        this.players = new Player[]{player1, player2};
        this.turn = 1;
        this.time = new HashMap<Player, Long>();

        time.put(player1, 0l);
        time.put(player2, 0l);

        for(Player p : players) p.newGame();

    }

    public Position getNextPlayerMove(GameBoard currentGameBoard, LinkedList<Position> legalPositions) {
        noReturnFromPlayer = true;

        Player currentPlayer = players[turn];
        currentPlayer.currentBoard = new GameBoard(currentGameBoard.copyMatrix());
        currentPlayer.currentLegalPositions = copy(legalPositions);
        Position playerPosition = getMove(currentPlayer);

        if(playerMoveIsLegal(legalPositions, playerPosition)) {
            return playerPosition;
        }

        if(noReturnFromPlayer) this.setTimeLeft(currentPlayer,0l);

        return chooseARandomMove(legalPositions);
    }

    private LinkedList<Position> copy(LinkedList<Position> legalPositions) {
        LinkedList<Position> copied = new LinkedList<Position>();
        for(Position p : legalPositions) {
            copied.add(new Position(p.row,p.column));
        }
        return copied;
    }

    private boolean playerMoveIsLegal(LinkedList<Position> legalPositions, Position position) {
        if(position == null) return false;
        if(!legalPositions.contains(position)) return false;
        return true;
    }


    private Position chooseARandomMove(LinkedList<Position> positions) {
        if(positions.size() < 1) return null;
        return positions.getFirst();
    }


    private Position getMove(Player player) {

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Position> futureResult = service.submit(player);
        Position result = null;
        final long availableTime = TIMESLOT + getSavedTime(player);
        long timeBox = 0l;

        try{
            timeBox = System.currentTimeMillis();
            result = futureResult.get(availableTime, TimeUnit.MILLISECONDS);
            timeBox = System.currentTimeMillis() - timeBox;
            noReturnFromPlayer = false;
        }catch(Throwable t){
            futureResult.cancel(true);
            t.printStackTrace();
        }
        long diff = 0l;
        if(Math.abs(timeBox) < availableTime) diff = availableTime - timeBox;
        setTimeLeft(player, diff);
        service.shutdown();
        return result;
    }


    public void restart() {
        turn = 0;
    }

    public Player getPreviousPlayer() {
        return players[(turn + 1) % 2];
    }

    public void setTimeLeft(Player player, long l) {

        this.time.put(player, l);
    }

    public long getSavedTime(Player p) {
        return this.time.get(p);
    }
}
