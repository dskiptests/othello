package gameschedule;


public class Match {

    public final String whitePlayer;
    public final String whiteScore;
    public final String blackPlayer;
    public final String blackScore;

    public Match(String whitePlayer, String whiteScore, String blackPlayer, String blackScore) {
        this.whitePlayer = whitePlayer;

        if(whiteScore.equals("0")) this.whiteScore = "-";
        else this.whiteScore = String.valueOf(whiteScore);

        this.blackPlayer = blackPlayer;
        if(blackScore.equals("0")) this.blackScore = "-";
        else this.blackScore = String.valueOf(blackScore);
    }
}


