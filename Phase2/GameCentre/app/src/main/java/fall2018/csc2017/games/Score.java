package fall2018.csc2017.games;

public class Score {
    private String username;
    private int score;

    public Score(String username, int score){
        this.username = username;
        this.score = score;
    }

    public Score(int score){
        this.username = null;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }


}
