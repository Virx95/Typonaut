package ee.technest.typonaut.controller.dto.objects;

import java.util.List;


public class Result {

    private String yourName;
    private String opponentName;
    private long yourScore;
    private long opponentScore;
    private List<Line> games;

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public long getYourScore() {
        return yourScore;
    }

    public void setYourScore(long yourScore) {
        this.yourScore = yourScore;
    }

    public long getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(long opponentScore) {
        this.opponentScore = opponentScore;
    }

    public List<Line> getGames() {
        return games;
    }

    public void setGames(List<Line> games) {
        this.games = games;
    }
}
