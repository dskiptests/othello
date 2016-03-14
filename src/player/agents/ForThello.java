/**
 * Created by ericleijonmarck on 2015-12-18.
 */
package player.agents;

import game.GameBoard;
import game.Position;
import player.Agent;
import game.Color;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ForThello extends Agent {


    private Random random;
    private int boardSize;
    public List<TurnsData> listTurnsData;

    public ForThello(Color color) {
        super(color);

    }


    @Override
    public void newGame() {
        this.random = new Random();
        this.boardSize = 8;
        listTurnsData = new ArrayList<TurnsData>();
    }

    public int NumberOfCoinsGainedPerMove(GameBoard current, GameBoard next) {
        int Coins  = 0;
        int NextCoins  = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.color == current.getBoardMatrix()[i][j]){
                    Coins++;
                }

                if (this.color == next.getBoardMatrix()[i][j]){
                    NextCoins ++;
                }
            }
            }
        return NextCoins - Coins;
    }

    public void algoMove(GameBoard board, TurnsData turnsData) {

        turnsData.Depth ++;
        GameBoard oldBoard = board.copyBoard();
        LinkedList<Position> availableMoves;
        Color color;
        if (turnsData.Depth == 1 || turnsData.Depth == 3){
            availableMoves = oldBoard.getAllLegalPositions(this.color);
            color = this.color;
        }
        else {
            availableMoves = oldBoard.getAllLegalPositions(getOppositeColor());
            color = getOppositeColor();
        }
        availableMoves = oldBoard.getAllLegalPositions(this.color);

        for(Position p : availableMoves) {

            GameBoard tempBoard = oldBoard.copyBoard();
            tempBoard.placeDisk(color, p);
            turnsData.CoinsFlipped += NumberOfCoinsGainedPerMove(oldBoard,tempBoard);

            if (color == this.color) {
                if ((p.row % 7) == 0 && (p.column % 7) == 0)
                    turnsData.CoinsFlipped += 1000;
                if ((p.row == 1 && p.column == 0) || (p.row == 1 && p.column == 1) || (p.row == 0 && p.column == 1))
                    turnsData.CoinsFlipped -= 100;
                if ((p.row == 0 && p.column == 6) || (p.row == 1) && (p.column == 6) || (p.row == 1) && (p.column == 7))
                    turnsData.CoinsFlipped -= 100;
                if ((p.row == 6 && p.column == 0) || (p.row == 6) && (p.column ==1) || (p.row == 7) && (p.column == 1))
                    turnsData.CoinsFlipped -= 100;
                if ((p.row == 6 && p.column == 6) || (p.row == 7) && (p.column ==6) || (p.row == 6) && (p.column == 7))
                    turnsData.CoinsFlipped -= 100;
            }

            if ( turnsData.Depth < 4){
                algoMove(tempBoard,turnsData);
            }
            else {
                listTurnsData.add(turnsData);
            }
        }

    }

    @Override
    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        int randomIndex = random.nextInt(currentLegalPositions.size());
        Position choosenPosition = this.currentLegalPositions.get(randomIndex);
        Color oppositeColor = getOppositeColor();

        int maxNumberOfMoves = Integer.MAX_VALUE;
        for(Position p : this.currentLegalPositions) {

            TurnsData turnsData = new TurnsData(p);

            GameBoard tempBoard = this.currentBoard.copyBoard();
            tempBoard.placeDisk(this.color, p);
            turnsData.CoinsFlipped += NumberOfCoinsGainedPerMove(this.currentBoard,tempBoard);

            algoMove(tempBoard,turnsData);

        }
        Position bestPosition;
        bestPosition = listTurnsData.get(0).FirstMove;
        int bestScore = 0;
        for(TurnsData t: listTurnsData){

            if (bestScore < t.CoinsFlipped) {
                bestScore = t.CoinsFlipped;
                bestPosition = t.FirstMove;
            }
        }

        choosenPosition = bestPosition;
        return choosenPosition;
    }
    private Color getOppositeColor() {
        if(this.color == Color.WHITE) return Color.BLACK;
        return Color.WHITE;
    }
}


