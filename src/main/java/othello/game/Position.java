package othello.game;

import java.util.Objects;

public class Position {

    private final int row;
    private final int column;
    private final int hashCode;

    private Position(final int row, final int column) {
        this.row = Math.abs(row) % 8;
        this.column = Math.abs(column) % 8;
        this.hashCode = (this.row + "" + this.column).hashCode();
    }

    public static Position create(final int row, final int column) {
        return new Position(row, column);
    }

    public static Position create(Position position) {
        Objects.requireNonNull(position, "Cannot copy null");
        return Position.create(position.row, position.column);
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

    public int row() {
        return this.row;
    }

    public int column() {
        return this.column;
    }

    public Position copy() {
        return new Position(row, column);
    }

    public static Position newPosition(Position position) {

        return new Position(position.row, position.column);
    }

}
