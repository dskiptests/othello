package scoreboard;


import player.Agent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ScoreBoard {

    private final String[] columnNames = {
            "PLAYER",
            "PLAYED",
            "WINS",
            "DRAWS",
            "LOSSES",
            "FOR",
            "AGAINST",
            "DIFF",
            "SCORE"};

    private HashMap<String, PlayerScore> scoreBoard = new HashMap<String, PlayerScore>();
    private JFrame jFrame;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel tableModel;
    private JButton jButton;
    private JPanel jPanel;

    public ScoreBoard() {
        createGUI();
    }

    private void createGUI() {
        this.jFrame = new JFrame("Othello Score Board");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tableModel = new DefaultTableModel(getData(), this.columnNames);
        this.jTable = new JTable(tableModel);
        this.jScrollPane = new JScrollPane(jTable);
        this.jButton = new JButton("Clear Score Board");
        this.jPanel = new JPanel();
        this.jPanel.add(this.jButton);
        this.jPanel.setVisible(true);
        this.jFrame.add(this.jPanel);

        jFrame.setSize(600, 600);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);
    }

    public void put(Agent agent1, int player1Score, Agent agent2, int player2Score) {

        if (agent1.NAME.equals(agent2.NAME)) return;

        updateScore(agent1, player1Score);
        updateScore(agent2, player2Score);

        clearAndAdd();
    }

    private void clearAndAdd() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        for (Object[] row : getData()) {
            this.tableModel.addRow(row);
        }
        this.tableModel.fireTableDataChanged();
    }

    private Object[][] getData() {
        Object[][] data = new Object[scoreBoard.size()][7];
        int index = 0;
        for (PlayerScore row : getSortedScores()) {
            data[index] = new Object[]{
                    scoreBoard.get(row.name).name,
                    scoreBoard.get(row.name).played,
                    scoreBoard.get(row.name).wins,
                    scoreBoard.get(row.name).draws,
                    scoreBoard.get(row.name).losses,
                    scoreBoard.get(row.name).plus,
                    scoreBoard.get(row.name).minus,
                    scoreBoard.get(row.name).diff,
                    scoreBoard.get(row.name).score
            };
            index++;
        }
        return data;
    }

    private List<PlayerScore> getSortedScores() {
        ArrayList<PlayerScore> scoreList = new ArrayList<PlayerScore>();
        for (String key : scoreBoard.keySet()) {
            scoreList.add(scoreBoard.get(key));
        }
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        return scoreList;
    }

    private void updateScore(Agent agent, int score) {
        if (this.scoreBoard.containsKey(agent.NAME)) {
            this.scoreBoard.put(agent.NAME, new PlayerScore(this.scoreBoard.get(agent.NAME), score));
        } else {
            this.scoreBoard.put(agent.NAME, new PlayerScore(agent.NAME, score));
        }
    }


    public void clear() {
        this.scoreBoard = new HashMap<String, PlayerScore>();
        clearAndAdd();
    }

    public void kill() {
        this.jFrame.hide();
    }

    public void show() {
        this.jFrame.show();
    }

}
