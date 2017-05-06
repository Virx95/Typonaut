package ee.technest.typonaut.json.objects;

import ee.technest.typonaut.modal.Status;

public class CountDown {

    private int counter;
    private Status status;

    public CountDown() {
        this.status = Status.COUNTDOWN;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
