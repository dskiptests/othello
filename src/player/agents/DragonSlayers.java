package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.*;

/**
 * Created by Brian on 2016-03-11.
 */
public class DragonSlayers extends Agent {

    private Random random;
    private int boardSize;
    private int count;

    public DragonSlayers(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        this.random = new Random();
        this.boardSize = 8;
        this.count = 0;
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        Position chosenPosition = null;
        int randomIndex = random.nextInt(currentLegalPositions.size());
        chosenPosition = this.currentLegalPositions.get(randomIndex);

        //boolean edgeCorner = false;
        for(Position m : this.currentLegalPositions) {
            if(coordinateIsACorner(m)) {
                //edgeCorner = true;
                chosenPosition = m;
                return new Position(chosenPosition);
            }
        }
        for(Position p : this.currentLegalPositions) {
            if(coordinateIsOnTheEdge(p)) {
                //edgeCorner = true;
                chosenPosition = p;
                return new Position(chosenPosition);
            }
        }
        if (board.getNumberOfDisksInColor(Color.EMPTY) < 17) {
            int tmp2 = 0;
            int j = 0;
            Position currPosition = null;
            for(Position p : currentLegalPositions){
                board.placeDisk(this.color,p);
                tmp2 = board.getNumberOfDisksInColor(this.color);
                if(tmp2 > j){

                    if (p.row == 2 && (p.column == 1 || p.column == 2 || p.column == 3 || p.column == 4)){
                        break;
                    }

                    if (p.row == 3 && (p.column == 2 || p.column==5)){
                        break;
                    }
                    if (p.row == 4 && (p.column == 2 || p.column==5)){
                        break;
                    }
                    j = tmp2;
                    currPosition = p;

                }
            }
            chosenPosition = currPosition;
            return new Position(chosenPosition);
        }




        int tmp = 0;
        int i = 100000;
        Position currPosition = null;
        for(Position p : currentLegalPositions){
            board.placeDisk(this.color,p);
            tmp = board.getNumberOfDisksInColor(this.color);
            if(tmp < i){

                if (p.row == 2 && (p.column == 1 || p.column == 2 || p.column == 3 || p.column == 4)){
                    break;
                }
                if (p.row == 3 && (p.column == 2 || p.column==5)){
                    break;
                }
                if (p.row == 4 && (p.column == 2 || p.column==5)){
                    break;
                }
                i = tmp;
                currPosition = p;
            }
        }
        chosenPosition = currPosition;
        return new Position(chosenPosition);
    }

    private boolean coordinateIsACorner(Position position) {
        int row = position.row % (this.boardSize - 1);
        int col = position.column % (this.boardSize - 1);

        if(row == 0 && col == 0) return true;

        return false;
    }

    private boolean coordinateIsOnTheEdge(Position position) {
        int row = position.row % (this.boardSize - 1);
        int col = position.column % (this.boardSize - 1);

        if(row == 0 || col == 0) return true;
        return false;
    }
}
