package othello.game;

public enum Color {

    BLACK ("Black"),
    WHITE ("White"),
    EMPTY ("Empty");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    /**
     *
     * @param color
     * @return If param equals EMPTY, EMPTY is returned. Otherwise the opposite color is returned.
     */
    public static Color opposite(Color color) {
        if(color.equals(EMPTY)) return EMPTY;

        return color == BLACK ? WHITE : BLACK;
    }
}





