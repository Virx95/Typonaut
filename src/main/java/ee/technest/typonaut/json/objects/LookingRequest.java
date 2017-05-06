package ee.technest.typonaut.json.objects;

import ee.technest.typonaut.modal.Status;


public class LookingRequest {

    private String name;
    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
