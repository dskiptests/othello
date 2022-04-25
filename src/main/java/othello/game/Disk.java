package othello.game;

/**
 * Created by david on 2015-11-07.
 */
public record Disk(Color color) {


    public static Disk create(Color color) {
        return new Disk(color);
    }
}
