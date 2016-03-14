package gameschedule;

import player.Agent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by david on 10/03/16.
 */
public class GameSchedule {

    private final String[] columnNames = {"WHITE", "SCORE", "BLACK", "SCORE"};

    private HashMap<String, Match> games = new HashMap<String, Match>();
    private JFrame jFrame;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel tableModel;
    private JPanel jPanel;

    public GameSchedule() {
        createGUI();
    }

    private void createGUI() {
        this.jFrame = new JFrame("Statistics");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tableModel = new DefaultTableModel(getData(), this.columnNames);
        this.jTable = new JTable(tableModel);
        this.jScrollPane = new JScrollPane(jTable);

        this.jPanel = new JPanel();
        this.jPanel.setVisible(true);
        this.jFrame.add(this.jPanel);

        jFrame.setSize(600, 600);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);
    }

    public void put(Agent agent1, int player1Score, Agent agent2, int player2Score) {

        this.put(agent1.NAME, String.valueOf(player1Score), agent2.NAME, String.valueOf(player2Score));
    }
    public void put(Match match) {


        this.put(match.whitePlayer, match.whiteScore, match.blackPlayer, match.blackScore);
    }

    public void put(String player1, String player1Score, String player2, String player2Score) {
        if(player1.equals(player2)) return;
        String key = player1 + player2;

        games.put(key, new Match(player1, player1Score, player2, player2Score));
        clearAndAdd();
    }

    private void clearAndAdd() {
        while(tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        for (Object[] row : getData()) {
            this.tableModel.addRow(row);
        }
        this.tableModel.fireTableDataChanged();
    }

    private Object[][] getData() {
        Object[][] data = new Object[games.size()][4];
        int index = 0;
        for (Match row : getSortedScores()) {
            data[index] = new Object[] {
                    games.get(row.whitePlayer + row.blackPlayer).whitePlayer,
                    games.get(row.whitePlayer + row.blackPlayer).whiteScore,
                    games.get(row.whitePlayer + row.blackPlayer).blackPlayer,
                    games.get(row.whitePlayer + row.blackPlayer).blackScore,
            };
            index++;
        }
        return data;
    }

    public List<Match> getSortedScores() {
        ArrayList<Match> matchList = new ArrayList<Match>();
        for (String key : games.keySet() ) {
            matchList.add(games.get(key));
        }
        return matchList;
    }



    public void clear() {
        this.games = new HashMap<String, Match>();
        clearAndAdd();
    }


    public HashMap<String, Match> matches() {
        return games;
    }

    public void kill() {
        this.jFrame.hide();
    }

    public void show() {
        this.jFrame.show();
    }
}
