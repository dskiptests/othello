package game;

import java.util.LinkedList;

public class GameBoard {

    public final int BOARD_SIZE = 8;
    private final Color[][] boardMatrix;



    public GameBoard() {
        this.boardMatrix = new Color[BOARD_SIZE][BOARD_SIZE];
        setUpNewGame();
    }

    public GameBoard(Color[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
    }


    private void setUpNewGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardMatrix[i][j] = Color.EMPTY;
            }
        }

        boardMatrix[3][3] = Color.WHITE;
        boardMatrix[4][4] = Color.WHITE;
        boardMatrix[3][4] = Color.BLACK;
        boardMatrix[4][3] = Color.BLACK;
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



    public Color[][] getBoardMatrix() {
        return boardMatrix;
    }


    public Color[][] copyMatrix() {
        Color[][] matrix = new Color[BOARD_SIZE][BOARD_SIZE];
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

    public LinkedList<Position> getAllLegalPositions(Color color) {
        LinkedList<Position> legalPositions = new LinkedList<Position>();
        String m = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isLegalMove(color, new Position(i, j))) {
                    if(boardMatrix[i][j] == Color.EMPTY) {
                        legalPositions.add(new Position(i, j));
                    }
                }
            }
        }
        return legalPositions;
    }

    public void placeDisk(Color color, Position position) {
        flip(color, position, true);
    }

    public int getNumberOfDisksInColor(Color color) {
        int count = 0;
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(boardMatrix[i][j] == color) count++;
            }
        }
        return count;
    }

    public boolean isLegalMove(Color color, Position position) {
        return flip(color, position, false);
    }

    private boolean flip(Color color, Position position, boolean flip) {

        boolean isValid = false;
        for (int chkRow = -1; chkRow < 2; chkRow++) {
            for (int chkCol = -1; chkCol < 2; chkCol++) {
                if (chkRow == 0 && chkCol == 0) {
                    continue;
                }
                int xRow = position.row + chkRow;
                int xCol = position.column + chkCol;

                if (xRow >= 0 && xRow <= 7 && xCol >= 0 && xCol <= 7) {
                    if ((boardMatrix[xRow][xCol]) == (color == Color.BLACK ? Color.WHITE : Color.BLACK)) {
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
        return (0 == getNumberOfDisksInColor(Color.EMPTY));
    }


    public Color getPositionColor(Position position) {
        return getBoardMatrix()[position.row][position.column];
    }
}
