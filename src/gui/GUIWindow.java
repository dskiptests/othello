package gui;

import game.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.LinkedList;

public class GUIWindow {

    private static final Color RED = new Color(140,40,40);
    private static final Color GREEN = new Color(0, 100, 0);

    private ImageIcon picture;
    private JPanel slotsPanel = new JPanel(new GridLayout(8, 8));
    private JPanel[][] panelBoard = new JPanel[8][8];
    private JLabel txtCurrentPlayer = new JLabel("Black");
    private JLabel txtWhiteScore = new JLabel(" __ ");
    private JLabel txtBlackScore = new JLabel(" __ ");
    private int wCount = 0;
    private int bCount = 0;
    private Game game;




    public GUIWindow() {

        CreateUI();
    }

    public void CreateUI() {
        JFrame window = new JFrame("Othello");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(200, 200, 800, 700);
        window.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(CreateDisplay(), BorderLayout.CENTER);
        panel.add(CreateMenu(), BorderLayout.EAST);
        window.add(panel, BorderLayout.CENTER);
        window.setVisible(true);

    }

    private JPanel CreateMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 0));
        JButton newGame = new JButton("Start New Game");
        JButton white = new JButton("White Move");
        JButton black = new JButton("Black Move");
        TitledBorder border = new TitledBorder("Menu");
        newGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        panel.add(newGame);
        Border border2 = BorderFactory.createTitledBorder("Pizza Toppings");
        panel.setBorder(border2);
        JCheckBox check = new JCheckBox("Anchovies");


        panel.add(check, check);
        panel.setBorder(border);



        return panel;
    }

    private JPanel CreateDisplay() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JLabel txtWhite = new JLabel("White : ");
        JLabel txtBlack = new JLabel("Black : ");

        TitledBorder border = new TitledBorder("Display");
        JButton actionButton = new JButton("Turn!");
        JLabel txtCurrent = new JLabel("   Current Player: ");

        JLabel blackPic = new JLabel();
        JLabel whitePic = new JLabel();
        mainPanel.setBorder(border);



        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Move m = game.next();
                if(m == null) {
                    game.switchPlayer();

                }
                else if (game.board.getBoard()[m.row][m.column] == COLOR.EMPTY) {

                    if (game.board.doFlip(m, false)) {
                        game.board.doFlip(m, true);
                        game.board.placeDisk(m);
                    } else if (!game.isLegalMove(new Move(m.row, m.column))) {
                        GUIConsole.display("Not a valid move: ");
                    }

                    updateStatus();
                    update();


                }

            }

        });

        panel2.add(txtWhite);
        panel2.add(txtWhiteScore);
        panel2.add(actionButton);
        panel2.add(txtBlack);
        panel2.add(txtBlackScore);
        panel2.add(txtCurrent);
        panel2.add(txtCurrentPlayer);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Border black = BorderFactory.createLineBorder(Color.black);
                final JPanel slots = new JPanel();
                slots.setBorder(black);
                slots.setBackground(GREEN);
                final int row = i;
                final int col = j;
                slots.addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent me) {
                        if (game.board.getBoard()[row][col] == COLOR.EMPTY) {
                            Move m = game.next();

                            if(m == null) {
                                game.switchPlayer();

                            }
                            else if (game.board.doFlip(m, false)) {
                                game.board.doFlip(m, true);
                                game.board.placeDisk(m);
                            } else if (!game.isLegalMove(new Move(row, col))) {
                                GUIConsole.display("Not a valid move: ");
                            }

                            updateStatus();
                            update();
                        }
                    }
                    public void mousePressed(MouseEvent me) {
//                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                    public void mouseReleased(MouseEvent me) {
//                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                    public void mouseEntered(MouseEvent me) {
//                        throw new UnsupportedOperationException("Not supported yet.");
                        slots.setBackground(RED);
                    }
                    public void mouseExited(MouseEvent me) {
//                        throw new UnsupportedOperationException("Not supported yet.");
                        slots.setBackground(GREEN);
                    }
                });
                slotsPanel.add(slots);
                panelBoard[i][j] = slots;
            }
        }

        mainPanel.add(slotsPanel, BorderLayout.CENTER);
        mainPanel.add(panel2, BorderLayout.SOUTH);
        return mainPanel;
    }

    public void setPicture(String color, int row, int col) {

        if (color.equalsIgnoreCase("Black")) {
            picture = createImageIcon("./black.png");
            JLabel picLabel = new JLabel((picture));
            panelBoard[row][col].removeAll();
            panelBoard[row][col].add(picLabel);

        } else if (color.equalsIgnoreCase("White")) {
            picture = createImageIcon("./white.png");
            JLabel picLabel = new JLabel((picture));
            panelBoard[row][col].removeAll();
            panelBoard[row][col].add(picLabel);

        } else {
            panelBoard[row][col].removeAll();
            panelBoard[row][col].setBackground(GREEN);
        }
    }

    public void update() {


        game.board.getAllLegalMoves();

        LinkedList<Move> legalMoves = new LinkedList<Move>();
        for (int i = 0; i < game.board.getBoard().length; i++) {
            for (int j = 0; j < game.board.getBoard()[i].length; j++) {
                panelBoard[i][j].removeAll();
                if (game.board.getBoard()[i][j] == COLOR.BLACK) {
                    setPicture("Black", i, j);
                }
                if (game.board.getBoard()[i][j] == COLOR.WHITE) {
                    setPicture("White", i, j);
                }
                if (game.board.getBoard()[i][j] == COLOR.EMPTY) {
                    setPicture("Green", i, j);
                }
                if (game.isLegalMove(new Move(i, j))) {
                    legalMoves.add(new Move(i,j));
                    picture = createImageIcon("./legalSlot.png");
                    JLabel picLabel = new JLabel((picture));
                    panelBoard[i][j].add(picLabel);
                }
            }
        }
        String moves = "";
        for(Move m : legalMoves) {moves +=  m.toString() + " ";}


        if(game.isFinished()) {
            if (wCount > bCount){
                GUIConsole.display("The winner is : Player2 (White)");
            }else if(wCount < bCount){
                GUIConsole.display("The winner is : Player1 (Black)");
            }else{
                GUIConsole.display("Draw .... dan tan tan");
            }
        }


        slotsPanel.updateUI();
    }

    public void restart() {

        game = new Game();
        update();
        slotsPanel.updateUI();
        updateStatus();

    }

    public void updateStatus() {
        wCount = 0;
        bCount = 0;

        String text = game.board.getCurrentPlayer().NAME + " " + game.board.getCurrentPlayer().getColor();

        txtCurrentPlayer.setText(text);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.board.getBoard()[i][j] == COLOR.WHITE) {
                    wCount++;
                }
                if (game.board.getBoard()[i][j] == COLOR.BLACK) {
                    bCount++;
                }
            }
        }
        txtWhiteScore.setText("" + wCount);
        txtBlackScore.setText("" + bCount);
    }

    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find the file: " + path);
            return null;
        }
    }

}
