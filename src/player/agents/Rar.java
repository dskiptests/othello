package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.ArrayList;
import java.util.LinkedList;

public class Rar extends Agent {
    int[][] t = new int[8][8];
    Color ourColor;
    Color enemyColor;
    ArrayList<Agent> list;

    public Rar(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        ourColor = this.color;
        enemyColor = getAntiColor(this.color);

        list = new ArrayList<>();

        EdgeEddie ee = new EdgeEddie(this.color);
        ForThello ft = new ForThello(this.color);
        MinimizingMaria mm = new MinimizingMaria(this.color);
        RandomRichard rr = new RandomRichard(this.color);
        Skumtomtarna st = new Skumtomtarna(this.color);
        //list.add(new DeepOthello(this.myColor));
        list.add(ee);
        list.add(ft);
        list.add(mm);
        list.add(rr);
        list.add(st);
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        double bestUtility = Double.MIN_VALUE;
        Position bestMove = null;
        for (Agent p : list) {
            GameBoard newBoard = board.copyBoard();
            Position pos = p.nextMove(board, currentLegalPositions);
            newBoard.placeDisk(ourColor, pos);
            double utility = utility(newBoard, ourColor);
            if (utility > bestUtility) {
                bestUtility = utility;
                bestMove = pos;
            }
        }

        return bestMove;
    }

    public Color getAntiColor(Color color) {
        if(color == Color.BLACK) {
            return Color.WHITE;
        } else if (color == Color.WHITE){
            return Color.BLACK;
        } else {
            return Color.EMPTY;
        }
    }

    private double utility(GameBoard board, Color color) {

        //remember to do something with myColor

        double colorSign = -1;
        if (color.equals(ourColor))
            colorSign = 1;

        double numOur = 0;
        double numEnemy = 0;
        Color[][] bordMatrix = board.getBoardMatrix();
        for (int rows = 0; rows < bordMatrix.length; rows++) {
            for (int cols = 0; cols < bordMatrix[0].length; cols++) {
                if (bordMatrix[rows][cols].equals(ourColor)) {

                    numOur++;
                }

                if (bordMatrix[rows][cols].equals(enemyColor))
                    numEnemy++;

            }

        }
        // System.err.println("Num our: " + numOur);
        double quote =  (numOur / numEnemy) * 10;

        if (board.gameIsFinished() && numOur > numEnemy) {
            quote += 100000;

        }

        else if (board.gameIsFinished() && numOur < numEnemy)
            quote -= 100000;

        // System.err.println(colorSign*quote);
        return colorSign * quote;

    }
}
