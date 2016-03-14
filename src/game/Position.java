package game;


public class Position {

    public final int row;
    public final int column;
    private final int hashCode;

    public Position(final int row, final int column) {
        this.row = Math.abs(row) % 8;
        this.column = Math.abs(column) % 8;
        this.hashCode = (this.row + "" + this.column).hashCode();
    }

    public Position(Position p) {
        this.row = Math.abs(p.row) % 8;
        this.column = Math.abs(p.column) % 8;
        this.hashCode = (this.row + "" + this.column).hashCode();
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }


    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        return obj.hashCode() == this.hashCode;
    }

    public Position copy() {
        return new Position(row, column);
    }

    public static Position newPosition(Position position) {
        return new Position(position.row, position.column);
    }

}
