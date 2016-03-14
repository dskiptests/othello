package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by bystam on 11/03/16.
 */
public class TeamTakanashi extends Agent {
    Color myColor;
    Color enemyColor;
    int runs;

    public TeamTakanashi(Color myColor) {
        super(myColor);
        this.myColor = myColor;
        runs = 0;
        if (this.myColor == Color.BLACK) {
            enemyColor = Color.WHITE;
        }
        else enemyColor = Color.BLACK;
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        Position ul = new Position(0, 0);
        Position ur = new Position(0, 7);
        Position bl = new Position(7, 0);
        Position br = new Position(7, 7);

        if (board.isLegalMove(this.myColor, ul)) {
            return ul;
        }
        if (board.isLegalMove(this.myColor, ur)) {
            return ur;
        }
        if (board.isLegalMove(this.myColor, bl)) {
            return bl;
        }
        if (board.isLegalMove(this.myColor, br)) {
            return br;
        }
        if (runs < 10) {
            runs++;
            Random r = new Random();
            int i = r.nextInt(Math.abs(currentLegalPositions.size() - 1));
            return currentLegalPositions.get(i);
        }
        GameBoard test = board.copyBoard();
        Position position = destruction(currentLegalPositions, test, this.myColor);


        return position;
    }       
    private LinkedList gain(LinkedList<Position> currentLegalPositions, GameBoard testBoard, Color color) {
        int best = testBoard.getNumberOfDisksInColor(color);
        LinkedList<Position> bestPos = new LinkedList<>();

        for(Position position : currentLegalPositions) {
            testBoard.placeDisk(color, position);
            if (testBoard.getNumberOfDisksInColor(color) > best) {
                bestPos.clear();
                bestPos.add(position);
            }
            if (testBoard.getNumberOfDisksInColor(color) == best) {
                bestPos.add(position);
            }
        }
        return bestPos;
    }

    private Position corners(LinkedList<Position> bestPos) {
        int best = 0;
        Position move = null;
        for(Position position : bestPos) {
            if ((Math.abs(position.row - 3) + Math.abs(position.column - 3)) > best) {
                move = position;
            }
        }
        return move;
    }

    private Position destruction(LinkedList<Position> currentLegalPositions, GameBoard testBoard, Color color) {
        int best = -1000;
        Position bestPos = null;
        for(Position position : currentLegalPositions) {
            GameBoard test = testBoard.copyBoard();
            test.placeDisk(color, position);
            int their = fu(test);
            int our = testBoard.getNumberOfDisksInColor(color);
            if (our-their > best) {
                best = our - their;
                bestPos = position;
            }
        }
        return bestPos;
    }

    private int fu(GameBoard testBoard) {
        LinkedList<Position> currentLegalPositions = testBoard.getAllLegalPositions(enemyColor);
        int best = testBoard.getNumberOfDisksInColor(enemyColor);
        for(Position position : currentLegalPositions) {
            GameBoard test = testBoard.copyBoard();
            test.placeDisk(enemyColor, position);
            int result = testBoard.getNumberOfDisksInColor(enemyColor);
            if (result > best) {
                best = result;
            }
        }
        return best;
    }
}
