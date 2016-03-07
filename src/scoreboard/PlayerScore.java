package scoreboard;


public class PlayerScore implements Comparable<PlayerScore> {


    public final int played;
    public final int wins;
    public final int losses;
    public final int draws;
    public final int plus;
    public final int minus;
    public final int score;
    public final String name;
    public final int diff;


    public PlayerScore(PlayerScore playerScore, int score) {
        this.name = playerScore.name;
        this.played = playerScore.played + 1;
        this.minus = 64 - score + playerScore.minus;
        this.plus = score + playerScore.plus;

        if(score > 32) {
            this.wins = playerScore.wins + 1;
            this.losses = playerScore.losses;
            this.draws = playerScore.draws;
            this.score = playerScore.score + 3;
        } else if(score < 32) {
            this.wins = playerScore.wins;
            this.losses = playerScore.losses + 1;
            this.draws = playerScore.draws;
            this.score = playerScore.score;
        } else {
            this.wins = playerScore.wins;
            this.losses = playerScore.losses;
            draws = playerScore.draws + 1;
            this.score = playerScore.score + 1;
        }

        this.diff = this.plus - this.minus;
    }

    public PlayerScore(String name, int score) {

        this.name = name;
        this.played = 1;
        this.minus = 64 - score;
        this.plus = score;

        if(score > 32) {
            this.wins = 1;
            this.losses = 0;
            this.draws = 0;
            this.score = 3;
        } else if(score < 32) {
            this.wins = 0;
            this.losses = 1;
            this.draws = 0;
            this.score = 0;
        } else {
            this.wins = 0;
            this.losses = 0;
            draws = 1;
            this.score = 1;
        }

        this.diff = this.plus - this.minus;
    }


    @Override
    public int compareTo(PlayerScore other) {
        if (this.score > other.score)
            return 1;
        else if (this.score < other.score)
            return -1;
        else {
            if (this.diff > other.diff)
                return 1;
            else if (this.diff < other.diff)
                return -1;
            else {
                if (this.wins > other.wins)
                    return 1;
                else if (this.wins < other.wins)
                    return -1;
                else {
                    if (this.plus > other.plus)
                        return 1;
                    else if (this.plus < other.plus)
                        return -1;
                    else {
                        return 0;
                    }
                }
            }

        }
    }
}
