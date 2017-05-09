package ee.technest.typonaut.controller.dto.objects;


import ee.technest.typonaut.modal.Status;

public class Line {

    private String word;
    private String yourTime;
    private String opponentTime;
    private Status status;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getYourTime() {
        return yourTime;
    }

    public void setYourTime(String yourTime) {
        this.yourTime = yourTime;
    }

    public String getOpponentTime() {
        return opponentTime;
    }

    public void setOpponentTime(String opponentTime) {
        this.opponentTime = opponentTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
