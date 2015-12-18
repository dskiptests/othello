package player;


import game.COLOR;
import game.Position;

import java.util.LinkedList;

public class Board {

    private final int SIZE = 8;
    private final COLOR[][] boardMatrix;

    public Board() {
        this.boardMatrix = new COLOR[SIZE][SIZE];

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

    public Board(COLOR[][] matrix) {
        this.boardMatrix = matrix;
    }

    public COLOR[][] getColorMatrix() {
        COLOR[][] matrix = new COLOR[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                matrix[i][j] = this.boardMatrix[i][j];
            }
        }
        return matrix;
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

    public LinkedList<Position> getAllLegalMoves(COLOR color) {
        LinkedList<Position> positions = new LinkedList<Position>();
        String m = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isLegalMove(color, new Position(i, j))) {
                    if(boardMatrix[i][j] == COLOR.EMPTY) {
                        positions.add(new Position(i,j));
                    }
                }


            }
        }
        return positions;
    }

    public boolean gameIsFinished() {
        int slotsLeft = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardMatrix[i][j] == COLOR.EMPTY) {
                    slotsLeft++;
                }


            }
        }
        return (0 == slotsLeft);
    }

    private boolean isLegalMove(COLOR color, Position position) {
        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }

                int xRow = position.row + chkRow;
                int xCol = position.column + chkCol;
                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (color == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = position.row + range * chkRow;
                            int nCol = position.column + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }
                            if (boardMatrix[nRow][nCol] == color) {

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


    public Board copy() {
        return new Board(getColorMatrix());
    }

    public boolean placeDisk(COLOR color, Position position) {

        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }
                int xRow = position.row + chkRow;
                int xCol = position.column + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (color == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
                        for (int range = 0; range < 8; range++) {

                            int nRow = position.row + range * chkRow;
                            int nCol = position.column + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            if (boardMatrix[nRow][nCol] == color) {
                                if (true) {
                                    for (int flipDistance = 1; flipDistance < range; flipDistance++) {
                                        int finalRow = position.row + flipDistance * chkRow;
                                        int finalCol = position.column + flipDistance * chkCol;

                                        boardMatrix[finalRow][finalCol] = color;
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


}
