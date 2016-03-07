package scoreboard;
import player.Player;
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

    public void put(Player player1, int player1Score, Player player2, int player2Score) {
        updateScore(player1, player1Score);
        updateScore(player2, player2Score);
    }

    private void updateScore(Player player, int score) {
        if(this.scoreBoard.containsKey(player.NAME)) {
            this.scoreBoard.put(player.NAME, new PlayerScore(this.scoreBoard.get(player.NAME), score));
        } else {
            this.scoreBoard.put(player.NAME, new PlayerScore(player.NAME, score));
        }
    }
}
