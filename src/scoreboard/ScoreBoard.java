package scoreboard;


import player.Player;

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

    public ScoreBoard() {
        createGUI();
    }

    private void createGUI() {
        this.jFrame = new JFrame("Othello Score Board");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tableModel = new DefaultTableModel(getData(), this.columnNames);
        this.jTable = new JTable(tableModel);
        this.jScrollPane = new JScrollPane(jTable);


        jFrame.setSize(600, 600);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);
    }

    public void put(Player player1, int player1Score, Player player2, int player2Score) {
        updateScore(player1, player1Score);
        updateScore(player2, player2Score);

        while(tableModel.getRowCount() > 0) {
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
            data[index] = new Object[] {
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
        for (String key : scoreBoard.keySet() ) {
            scoreList.add(scoreBoard.get(key));
        }
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        return scoreList;
    }

    private void updateScore(Player player, int score) {
        if(this.scoreBoard.containsKey(player.NAME)) {
            this.scoreBoard.put(player.NAME, new PlayerScore(this.scoreBoard.get(player.NAME), score));
        } else {
            this.scoreBoard.put(player.NAME, new PlayerScore(player.NAME, score));
        }
    }



}
