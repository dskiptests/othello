package game;
import player.*;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;

public class PlayerHandler {

    private final Agent[] agents;
    private int turn;
    public final long TIMESLOT;
    private Map<Agent, Long> time;
    private boolean noReturnFromPlayer;



    public Agent getPlayerByColor(Color color) {
        if(agents[0].color == color) {
            return agents[0];
        } else return agents[1];
    }

    public Agent getCurrentPlayer() {

        return agents[turn];
    }


    public Agent getCurrentPlayerForGUI() {
        return agents[(turn+1) % 2];

    }


    public Agent turn() {
        turn = (turn+1) % 2;
        return agents[turn];
    }

    public PlayerHandler(Agent agent1, Agent agent2, long timeslot) {
        this.TIMESLOT = timeslot;
        this.agents = new Agent[]{agent1, agent2};
        this.turn = 1;
        this.time = new HashMap<Agent, Long>();

        time.put(agent1, 0l);
        time.put(agent2, 0l);

        for(Agent p : agents) p.newGame();

    }

    public Position getNextPlayerMove(GameBoard currentGameBoard, LinkedList<Position> legalPositions) {
        noReturnFromPlayer = true;

        Agent currentAgent = agents[turn];
        currentAgent.currentBoard = new GameBoard(currentGameBoard.copyMatrix());
        currentAgent.currentLegalPositions = copy(legalPositions);
        Position playerPosition = getMove(currentAgent);

        if(playerMoveIsLegal(legalPositions, playerPosition)) {
            return playerPosition;
        }

        if(noReturnFromPlayer) this.setTimeLeft(currentAgent,0l);

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


    private Position getMove(Agent agent) {

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Position> futureResult = service.submit(agent);
        Position result = null;
        final long availableTime = TIMESLOT + getSavedTime(agent);
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
        setTimeLeft(agent, diff);
        service.shutdown();
        return result;
    }


    public void restart() {
        turn = 0;
    }

//    public Player getPreviousPlayer() {
//        return players[(turn + 1) % 2];
//    }

    public void setTimeLeft(Agent agent, long l) {

        this.time.put(agent, l);
    }

    public long getSavedTime(Agent p) {
        return this.time.get(p);
    }

}
