package game;
import player.Player;

import java.util.LinkedList;

public class Board {

   public final int SIZE = 8;
    private final COLOR[][] boardMatrix = new COLOR[SIZE][SIZE];
    public Player currentPlayer;


    public Board(Player firstPlayer) {
        this.currentPlayer = firstPlayer;
        setUpNewGame();
    }

    private void setUpNewGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardMatrix[i][j] = COLOR.EMPTY;
            }
        }

        boardMatrix[3][3] = COLOR.WHITE;
        boardMatrix[4][4] = COLOR.WHITE;
        boardMatrix[3][4] = COLOR.BLACK;
        boardMatrix[4][3] = COLOR.BLACK;
    }


    @Override
    public String toString() {
        String returnString = "";
        for(int row = 0; row < SIZE; row ++ ){
            for(int col = 0; col < SIZE; col++) {
                returnString += "(" + boardMatrix[row][col] + ") ";
            }
        }
        return returnString;
    }

    public void placeDisk(Move move) {

        System.out.println("Player " + currentPlayer.NAME + " insists on move: " + move.row + " " + move.column);

        boardMatrix[move.row][move.column] = currentPlayer.getColor();

    }

    public COLOR[][] getBoard() {
        return boardMatrix;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean chkSlot(int currentRow, int currentCol) {
        boolean flippable = false;

        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {

                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }

                int xRow = currentRow + chkRow;
                int xCol = currentCol + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (this.currentPlayer.getColor() == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {
                            int nRow = currentRow + range * chkRow;
                            int nCol = currentCol + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (boardMatrix[nRow][nCol] == this.currentPlayer.getColor()) {
                                flippable = true;
                                break;

                            }

                        }

                    }
                }


            }
        }
        return flippable;
    }


    public boolean isMoveLegal(Move move) {
        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }

                int xRow = move.row + chkRow;
                int xCol = move.column + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (this.currentPlayer.getColor() == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = move.row + range * chkRow;
                            int nCol = move.column + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (boardMatrix[nRow][nCol] == this.currentPlayer.getColor()) {

                                isValid = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return isValid;
    }

    public LinkedList<Move> getAllLegalMoves() {
        LinkedList<Move> moves = new LinkedList<Move>();
        String m = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMoveLegal(new Move(i, j))) {
                    if(boardMatrix[i][j] == COLOR.EMPTY) {
                        moves.add(new Move(i,j));
                    }
                }


            }
        }
        return moves;
    }

    public boolean doFlip(Move move, boolean doMove) {
        return doFlip(move.row, move.column, doMove);
    }

    private boolean doFlip(int currentRow, int currentCol, boolean doMove) {

        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }
                int xRow = currentRow + chkRow;
                int xCol = currentCol + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (this.currentPlayer.getColor() == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = currentRow + range * chkRow;
                            int nCol = currentCol + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (boardMatrix[nRow][nCol] == this.currentPlayer.getColor()) {
                                if (doMove) {
                                    for (int flipDistance = 1; flipDistance < range; flipDistance++) {
                                        int finalRow = currentRow + flipDistance * chkRow;
                                        int finalCol = currentCol + flipDistance * chkCol;

                                        boardMatrix[finalRow][finalCol] = this.currentPlayer.getColor();
                                    }
                                }
                                isValid = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return isValid;
    }




    public int chkWinner() {
        int slotsLeft = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardMatrix[i][j] == COLOR.EMPTY) {
                    slotsLeft++;
                }


            }
        }
        return slotsLeft;
    }
}
