package gui;
import game.*;

import java.util.LinkedList;

public class GameBoard {

    private COLOR[][] xBoard = new COLOR[8][8];
    private PlayerX player1 = new PlayerX(COLOR.BLACK);
    private PlayerX player2 = new PlayerX(COLOR.WHITE);
    private PlayerX currentPlayerX = player1;

    public GameBoard() {
        NewGame();
    }

    private void NewGame() {
        // create a new gameBoard  , all positions removed , score restart ;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                xBoard[i][j] = COLOR.EMPTY;

            }
        }

        placeDisk(new Move(3, 3));
        nextTurn();
        placeDisk(new Move(3, 4));
        nextTurn();
        placeDisk(new Move(4, 4));
        nextTurn();
        placeDisk(new Move(4, 3));
        nextTurn();
    }


    private String getBoardAsString() {

        for(int row = 0; row < xBoard.length; row ++ ){
            String print = "";
            for(int col = 0; col < xBoard.length; col++) {
                print += "(" + printDisk(xBoard[row][col]) + ") ";
            }

            System.out.println(print);

        }

        return "";
    }

    private COLOR printDisk(COLOR COLOR) {
        if(COLOR.toString().equals("EMPTY")) return COLOR.EMPTY;

        return COLOR;
    }





    public void placeDisk(Move move) {

        System.out.println("Player insists on move: " + move.row + " " + move.column);

        if (currentPlayerX.getColor() == COLOR.BLACK) {
            xBoard[move.row][move.column] = COLOR.BLACK;
        } else if (currentPlayerX.getColor() == COLOR.WHITE) {
            xBoard[move.row][move.column] = COLOR.WHITE;
        }
        getBoardAsString();

    }

    public COLOR[][] getBoard() {
        return xBoard;
    }

    public void nextTurn() {

        currentPlayerX = (currentPlayerX == player1) ? player2 : player1;
    }

    public PlayerX getCurrentPlayerX() {
        return currentPlayerX;
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
                    if ((xBoard[xRow][xCol]) == (this.currentPlayerX.getColor() == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {
                            int nRow = currentRow + range * chkRow;
                            int nCol = currentCol + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (xBoard[nRow][nCol] == this.currentPlayerX.getColor()) {
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





    public void newMove(Move move) {
        doFlip(move.row, move.column, true);


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
                    if ((xBoard[xRow][xCol]) == (this.currentPlayerX.getColor() == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = move.row + range * chkRow;
                            int nCol = move.column + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (xBoard[nRow][nCol] == this.currentPlayerX.getColor()) {

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
                    if(xBoard[i][j] == COLOR.EMPTY)
                        System.out.println(new Move(i,j) + " is a legal move for " + this.currentPlayerX.getColor());
                }


            }
        }
        System.out.println("All legal moves: " + m);

        return moves;
    }

    public boolean doFlip(int currentRow, int currentCol, boolean doMove) {

        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }

                int xRow = currentRow + chkRow;
                int xCol = currentCol + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((xBoard[xRow][xCol]) == (this.currentPlayerX.getColor() == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = currentRow + range * chkRow;
                            int nCol = currentCol + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (xBoard[nRow][nCol] == this.currentPlayerX.getColor()) {
                                if (doMove) {
                                    for (int flipDistance = 1; flipDistance < range; flipDistance++) {
                                        int finalRow = currentRow + flipDistance * chkRow;
                                        int finalCol = currentCol + flipDistance * chkCol;

                                        xBoard[finalRow][finalCol] = this.currentPlayerX.getColor();
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
                if (xBoard[i][j] == COLOR.EMPTY) {
                    slotsLeft++;
                }


            }
        }
        return slotsLeft;
    }
}
