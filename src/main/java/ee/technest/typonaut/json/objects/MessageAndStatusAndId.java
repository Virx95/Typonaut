package ee.technest.typonaut.json.objects;

import ee.technest.typonaut.modal.Status;


public class MessageAndStatusAndId {

    private Status status;
    private String message;
    private String id;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
