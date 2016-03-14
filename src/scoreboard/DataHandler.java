package scoreboard;
import player.Agent;
import java.util.HashMap;

public class DataHandler {

    private HashMap<String, PlayerScore> scoreBoard = new HashMap<String, PlayerScore>();

    public Object[][] getData() {
        Object[][] data = new Object[scoreBoard.size()][7];
        int index = 0;
        for (String key : scoreBoard.keySet() ) {
            data[index] = new Object[]{scoreBoard.get(key).name, scoreBoard.get(key).played, scoreBoard.get(key).wins, scoreBoard.get(key).draws, scoreBoard.get(key).losses, scoreBoard.get(key).plus, scoreBoard.get(key).minus, scoreBoard.get(key).score};
            index++;
        }
        return data;
    }

    public void put(Agent agent1, int player1Score, Agent agent2, int player2Score) {
        updateScore(agent1, player1Score);
        updateScore(agent2, player2Score);
    }

    private void updateScore(Agent agent, int score) {
        if(this.scoreBoard.containsKey(agent.NAME)) {
            this.scoreBoard.put(agent.NAME, new PlayerScore(this.scoreBoard.get(agent.NAME), score));
        } else {
            this.scoreBoard.put(agent.NAME, new PlayerScore(agent.NAME, score));
        }
    }
}
