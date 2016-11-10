package game;

import java.util.LinkedList;

public class GameBoard {

    public final int BOARD_SIZE = 8;
    private final Color[][] boardMatrix;


    /**
     * Create a new Board with the default starting state.
     */
    public GameBoard() {
        this.boardMatrix = new Color[BOARD_SIZE][BOARD_SIZE];
        setUpNewGame();
    }

    /**
     * Create a new Board with a certain state
     * @param boardMatrix
     */

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

    /**
     * Prints the Board
     * @return The Board Matrix as a String, each cell can have the values
     * "EMPTY", "BLACK", "WHITE"
     */
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

    /**
     * Returns the matrix that holds all the disks, changes made
     * on the returned object may change the state of the board.
     *
     * @return The matrix that holds all the disks.
     */
    public Color[][] getBoardMatrix() {
        return boardMatrix;
    }


    /**
     * Makes a copy of the matrix that holds all the disks. This matrix can be
     * modified without changing the state of the board.
     * @return A matrix
     */
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


    /**
     * Places a disk of desired color at a position and flips all disks
     * according the the rules. If the position inserted is not valid,
     * no changes will be made. This method changes the state of the board.
     *
     * @param color The color of the player
     * @param position The desired position of the new move
     */
    public void placeDisk(Color color, Position position) {
        flip(color, position, true);
    }

    /**
     * This method can be used to see how many disks of
     * a certain color the board contains.
     *
     * @param color The color of a player, can be Black, White or Empty.
     * @return Number of disks in the specified color, an integer between 0 and 64.
     */
    public int getNumberOfDisksInColor(Color color) {
        int count = 0;
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(boardMatrix[i][j] == color) count++;
            }
        }
        return count;
    }

    /**
     * The method can be used to check if it is possible to
     * place a disk at a certain position. By using this method,
     * no changes are made to the Board's state.
     *
     * @param color The color of the player whoms move it is
     * @param position The desired position
     * @return True if the move is legal, false otherwise
     */
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
                        for (int range = 1; range < 8; range++) {

                            int nRow = position.row + range * chkRow;
                            int nCol = position.column + range * chkCol;
                            if (nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }
			    if (boardMatrix[nRow][nCol] == Color.EMPTY) {
                                break;
                            } 
                            else if (boardMatrix[nRow][nCol] == color) {
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

    /**
     * The method only checks if there are empty positions left on the board
     * or not. In fact, the game could be over even if this method returns
     * false.
     *
     * @return True if there are no empty disks on the Board, false otherwise
     */
    public boolean gameIsFinished() {
        return 0 == getNumberOfDisksInColor(Color.EMPTY) || 0 == getNumberOfDisksInColor(Color.WHITE) || 0 == getNumberOfDisksInColor(Color.BLACK);
    }

    /**
     * @param position A position on the Board
     * @return The color of the position, can be Black, White or Empty.
     */
    public Color getPositionColor(Position position) {
        return getBoardMatrix()[position.row][position.column];
    }
}
