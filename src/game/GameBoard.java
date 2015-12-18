package game;
import player.Player;

import java.util.LinkedList;

public class GameBoard {

    public final int SIZE = 8;
    private final COLOR[][] boardMatrix = new COLOR[SIZE][SIZE];



    public GameBoard(Player firstPlayer) {
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

    public void placeDisk(Player player, Position position) {


        boardMatrix[position.row][position.column] = player.COLOR;

    }

    public COLOR[][] getBoard() {
        return boardMatrix;
    }


    public COLOR[][] copy() {
        COLOR[][] matrix = new COLOR[SIZE][SIZE];

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                matrix[i][j] = this.boardMatrix[i][j];
            }
        }
        return matrix;
    }




    public boolean isLegalMove(Player player, Position position) {
        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }

                int xRow = position.row + chkRow;
                int xCol = position.column + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (player.COLOR == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = position.row + range * chkRow;
                            int nCol = position.column + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (boardMatrix[nRow][nCol] == player.COLOR) {

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

    public LinkedList<Position> getAllLegalMoves(Player player) {
        LinkedList<Position> positions = new LinkedList<Position>();
        String m = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isLegalMove(player, new Position(i, j))) {
                    if(boardMatrix[i][j] == COLOR.EMPTY) {
                        positions.add(new Position(i,j));
                    }
                }


            }
        }
        return positions;
    }

    public boolean doFlip(Player player, Position position, boolean doMove) {
        return doFlip(player, position.row, position.column, doMove);
    }

    private boolean doFlip(Player player, int currentRow, int currentCol, boolean doMove) {



        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }
                int xRow = currentRow + chkRow;
                int xCol = currentCol + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (player.COLOR == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = currentRow + range * chkRow;
                            int nCol = currentCol + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (boardMatrix[nRow][nCol] == player.COLOR) {
                                if (doMove) {
                                    for (int flipDistance = 1; flipDistance < range; flipDistance++) {
                                        int finalRow = currentRow + flipDistance * chkRow;
                                        int finalCol = currentCol + flipDistance * chkCol;

                                        boardMatrix[finalRow][finalCol] = player.COLOR;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public COLOR getPosition(Position position) {
        return getBoard()[position.row][position.column];
    }
}
