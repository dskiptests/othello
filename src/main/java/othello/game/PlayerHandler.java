package othello.game;
import othello.player.*;
import othello.player.remote.RemoteAgent;


import java.util.*;
import java.util.concurrent.*;

public class PlayerHandler {

    private final Agent[] agents;
    private int turn;
    public final long timeslot;
    public final static long TIME_BOOST_FOR_REMOTE_AGENT = 1500;

    private Map<Agent, Long> time;
    private boolean noReturnFromPlayer;



    public Agent getPlayerByColor(Color color) {
        if(agents[0].color() == color) {
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
        this.timeslot = timeslot;
        this.agents = new Agent[]{agent1, agent2};
        this.turn = 1;
        this.time = new HashMap<Agent, Long>();

        time.put(agent1, 0l);
        time.put(agent2, 0l);

        for(Agent p : agents) p.newGame();

    }

    public Position getNextPlayerMove(GameBoard currentGameBoard, List<Position> legalPositions) {
        noReturnFromPlayer = true;

        final Agent currentAgent = agents[turn];
        currentAgent.currentBoard = new GameBoard(currentGameBoard.copyMatrix());
        currentAgent.currentLegalPositions = copy(legalPositions);
        final Position playerPosition = getMove(currentAgent);

        if(playerMoveIsLegal(legalPositions, playerPosition)) {
            return playerPosition;
        }

        if(noReturnFromPlayer) {
            this.setTimeLeft(currentAgent, 0L);
        }

        return chooseARandomMove(legalPositions);
    }

    private List<Position> copy(List<Position> legalPositions) {
        final List<Position> copied = new ArrayList<>();

        legalPositions.stream()
                .map(Position::copy)
                .forEach(copied::add);

        return copied;
    }

    private boolean playerMoveIsLegal(List<Position> legalPositions, Position position) {
        if(Objects.isNull(position)) {
            return false;
        } else if (!legalPositions.contains(position)) {
            return false;
        }
        return true;
    }


    private Position chooseARandomMove(List<Position> positions) {
        if(positions.isEmpty()) {
            return null;
        }
        return positions.get(0);
    }


    private Position getMove(final Agent agent) {

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Position> futureResult = service.submit(agent);
        Position result = null;
        final long availableTime = generateAvailableTime(agent);
        long timeBox = 0l;

        try{
            timeBox = System.currentTimeMillis();
            setAvailableTimeLeft(agent, availableTime);
            result = futureResult.get(availableTime, TimeUnit.MILLISECONDS);
            timeBox = System.currentTimeMillis() - timeBox;
            noReturnFromPlayer = false;
        } catch(Throwable t){
            futureResult.cancel(true);
            t.printStackTrace();
        }
        long diff = 0l;
        if(Math.abs(timeBox) < availableTime) {
            diff = availableTime - timeBox;
        }
        setTimeLeft(agent, diff);
        service.shutdown();
        return result;
    }

    private long generateAvailableTime(final Agent agent) {
        final long savedTime = timeslot + getSavedTime(agent);

        if(agent instanceof RemoteAgent) {
            return TIME_BOOST_FOR_REMOTE_AGENT + savedTime;
        }

        return savedTime;
    }

    private void setAvailableTimeLeft(final Agent agent, final long availableTime) {
        agent.setAvailableTimeLeft(availableTime);
    }


    public void restart() {
        turn = 0;
    }

//    public Player getPreviousPlayer() {
//        return players[(turn + 1) % 2];
//    }

    public void setTimeLeft(Agent agent, long timeLeft) {

        this.time.put(agent, timeLeft);
    }

    public long getSavedTime(Agent agent) {
        return this.time.get(agent);
    }

}
