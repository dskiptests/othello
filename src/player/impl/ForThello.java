/**
 * Created by ericleijonmarck on 2015-12-18.
 */
package player.impl;

import game.Position;
import player.Player;
import game.COLOR;
import player.Board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ForThello extends Player {


    private Random random;
    private int boardSize;
    public List<TurnsData> listTurnsData;

    public ForThello(game.COLOR color) {
        super(color);
        listTurnsData = new ArrayList<TurnsData>();
    }


    @Override
    public void newGame() {
        this.random = new Random();
        System.out.println(NAME + " is alive! :)");
        this.boardSize = 8;
    }

    public int NumberOfCoinsGainedPerMove(Board current, Board next) {
        int Coins  = 0;
        int NextCoins  = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.COLOR == current.getColorMatrix()[i][j]){
                    Coins++;
                }

                if (this.COLOR == next.getColorMatrix()[i][j]){
                    NextCoins ++;
                }
            }
            }
        return NextCoins - Coins;
    }

    public void algoMove(Board board, TurnsData turnsData) {

        turnsData.Depth ++;
        Board oldBoard = board.copy();
        LinkedList<Position> availableMoves;
        COLOR color;
        if (turnsData.Depth == 1 || turnsData.Depth == 3){
            availableMoves = oldBoard.getAllLegalMoves(this.COLOR);
            color = this.COLOR;
        }
        else {
            availableMoves = oldBoard.getAllLegalMoves(getOppositeColor());
            color = getOppositeColor();
        }
        availableMoves = oldBoard.getAllLegalMoves(this.COLOR);

        for(Position p : availableMoves) {

            Board tempBoard = oldBoard.copy();
            tempBoard.placeDisk(color, p);
            turnsData.CoinsFlipped += NumberOfCoinsGainedPerMove(oldBoard,tempBoard);

            if (color == this.COLOR) {
                if ((p.row % 7) == 0 && (p.column % 7) == 0)
                    turnsData.CoinsFlipped += 10000;
                if ((p.row == 1 && p.column == 0) || (p.row == 1 && p.column == 1) || (p.row == 0 && p.column == 1))
                    turnsData.CoinsFlipped -= 100;
                if ((p.row == 0 && p.column == 6) || (p.row == 1) && (p.column == 6) || (p.row == 1) && (p.column == 7))
                    turnsData.CoinsFlipped -= 100;
                if ((p.row == 6 && p.column == 0) || (p.row == 6) && (p.column ==1) || (p.row == 7) && (p.column == 1))
                    turnsData.CoinsFlipped -= 100;
                if ((p.row == 6 && p.column == 6) || (p.row == 7) && (p.column ==6) || (p.row == 6) && (p.column == 7))
                    turnsData.CoinsFlipped -= 100;
            }
            if (color == getOppositeColor()) {
                if ((p.row % 7) == 0 && (p.column % 7) == 0)
                    turnsData.CoinsFlipped -= 10000;
                if ((p.row == 1 && p.column == 0) || (p.row == 1 && p.column == 1) || (p.row == 0 && p.column == 1))
                    turnsData.CoinsFlipped += 100;
                if ((p.row == 0 && p.column == 6) || (p.row == 1) && (p.column == 6) || (p.row == 1) && (p.column == 7))
                    turnsData.CoinsFlipped += 100;
                if ((p.row == 6 && p.column == 0) || (p.row == 6) && (p.column ==1) || (p.row == 7) && (p.column == 1))
                    turnsData.CoinsFlipped += 100;
                if ((p.row == 6 && p.column == 6) || (p.row == 7) && (p.column ==6) || (p.row == 6) && (p.column == 7))
                    turnsData.CoinsFlipped += 100;
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
    public Position nextMove() {

        int randomIndex = random.nextInt(availablePositions.size());
        Position choosenPosition = this.availablePositions.get(randomIndex);
        COLOR oppositeColor = getOppositeColor();

        int maxNumberOfMoves = Integer.MAX_VALUE;
        for(Position p : this.availablePositions) {

            TurnsData turnsData = new TurnsData(p);

            Board tempBoard = this.currentBoard.copy();
            tempBoard.placeDisk(this.COLOR, p);
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

            // TODO: if t.FirstMove == corner:
            if ( (t.FirstMove.row % 8 == 0) && (t.FirstMove.column % 8 == 0) )
                return t.FirstMove;
            // DOIT!!!!
        }


        choosenPosition = bestPosition;
        return choosenPosition;
    }
    private COLOR getOppositeColor() {
        if(this.COLOR == game.COLOR.WHITE) return game.COLOR.BLACK;
        return COLOR.WHITE;
    }
}

class TurnsData {

    public int CoinsFlipped;
    public int Depth;
    public Position FirstMove;

    public TurnsData(Position firstMove){
        CoinsFlipped = 0;
        Depth = 0;
        FirstMove = firstMove;
    }

}
