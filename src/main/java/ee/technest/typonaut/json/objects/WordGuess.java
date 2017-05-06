package ee.technest.typonaut.json.objects;

import ee.technest.typonaut.modal.Status;


public class WordGuess {

    private Status status;
    private String word;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
