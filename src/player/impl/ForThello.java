/**
 * Created by ericleijonmarck on 2015-12-18.
 */
package player.impl;

import game.Position;
import player.Player;
import game.COLOR;
import player.Board;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class ForThello extends Player {


    private Random random;
    private int boardSize;
    public List<TurnsData> listTurnsData;

    public ForThello(game.COLOR color) {
        listTurnsData = new List<TurnsData>();
        super(color);
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
        bestPosition = listTurnsData;
        int bestScore = 0;
        for(TurnsData t: listTurnsData){

            if (bestScore < t.CoinsFlipped) {
                bestScore = t.CoinsFlipped;
                bestPosition = t.FirstMove;
            }
        }


        return choosenPosition;
    }
    private COLOR getOppositeColor() {
        if(this.COLOR == game.COLOR.WHITE) return game.COLOR.BLACK;
        return COLOR.WHITE;
    }
}

public class TurnsData {

    public int CoinsFlipped;
    public int Depth;
    public Position FirstMove;

    public TurnsData(Position firstMove){
        CoinsFlipped = 0;
        Depth = 0;
        FirstMove = firstMove;
    }

}
