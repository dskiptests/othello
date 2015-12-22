package game;

import java.util.LinkedList;

public class GameBoard {

    public final int BOARD_SIZE = 8;
    private final COLOR[][] boardMatrix;



    public GameBoard() {
        this.boardMatrix = new COLOR[BOARD_SIZE][BOARD_SIZE];
        setUpNewGame();
    }

    public GameBoard(COLOR[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
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
        for(int row = 0; row < BOARD_SIZE; row ++ ){
            for(int col = 0; col < BOARD_SIZE; col++) {
                returnString += "(" + boardMatrix[row][col] + ") ";
            }
        }
        return returnString;
    }



    public COLOR[][] getBoardMatrix() {
        return boardMatrix;
    }


    public COLOR[][] copyMatrix() {
        COLOR[][] matrix = new COLOR[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                matrix[i][j] = this.boardMatrix[i][j];
            }
        }
        return matrix;
    }

    public GameBoard copyBoard() {
        return new GameBoard(copyMatrix());
    }



//    public boolean isLegalMove(Player player, Position position) {
//        boolean isValid = false;
//        for (int chkRow = -1; chkRow < 2; chkRow++) {
//            for (int chkCol = -1; chkCol < 2; chkCol++) {
//                if (chkRow == 0 && chkCol == 0) {
//                    continue;
//                }
//                int xRow = position.row + chkRow;
//                int xCol = position.column + chkCol;
//                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
//                    if ((boardMatrix[xRow][xCol]) == (player.COLOR == COLOR.BLACK ? COLOR.WHITE : COLOR.BLACK)) {
//                        for (int range = 0; range < 8; range++) {
//                            int nRow = position.row + range * chkRow;
//                            int nCol = position.column + range * chkCol;
//                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
//                                continue;
//                            }
//                            if (boardMatrix[nRow][nCol] == player.COLOR) {
//
//                                isValid = true;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return isValid;
//    }

    public LinkedList<Position> getAllLegalPositions(COLOR color) {
        LinkedList<Position> legalPositions = new LinkedList<Position>();
        String m = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isLegalMove(color, new Position(i, j))) {
                    if(boardMatrix[i][j] == COLOR.EMPTY) {
                        legalPositions.add(new Position(i, j));
                    }
                }
            }
        }
        return legalPositions;
    }

    public void placeDisk(COLOR color, Position position) {
        flip(color, position, true);
    }

    public int getNumberOfDisksInColor(COLOR color) {
        int count = 0;
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(boardMatrix[i][j] == color) count++;
            }
        }
        return count;
    }

    public boolean isLegalMove(COLOR color, Position position) {
        return flip(color, position, false);
    }

    private boolean flip(COLOR color, Position position, boolean flip) {

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
                                if (flip) {
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

        if(flip && isValid) boardMatrix[position.row][position.column] = color;

        return isValid;
    }


    public boolean gameIsFinished() {
        return (0 == getNumberOfDisksInColor(COLOR.EMPTY));
    }


    public COLOR getPositionColor(Position position) {
        return getBoardMatrix()[position.row][position.column];
    }
}
