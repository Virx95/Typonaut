package ee.technest.typonaut.controller.dto.objects;


import ee.technest.typonaut.modal.Status;

public class Line {

    private String word;
    private long yourTime;
    private long opponentTime;
    private Status status;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getYourTime() {
        return yourTime;
    }

    public void setYourTime(long yourTime) {
        this.yourTime = yourTime;
    }

    public long getOpponentTime() {
        return opponentTime;
    }

    public void setOpponentTime(long opponentTime) {
        this.opponentTime = opponentTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
