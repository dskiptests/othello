package game;


public class Move {

    public final int row;
    public final int column;

    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
