package ee.technest.typonaut.json.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.technest.typonaut.modal.Status;

public class StatusRequest {

    private Status status;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
