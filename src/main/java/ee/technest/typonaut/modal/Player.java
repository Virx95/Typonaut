package ee.technest.typonaut.modal;


import ee.technest.typonaut.config.TimeoutTimer;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Player {

    private final WebSocketSession session;
    private final String id;
    private Status status;
    private String name;
    private String word;
    private int wordCounter = 0;
    private Player opponent;
    private long startTime;
    private long endTime;
    private TimeoutTimer timeoutTimer;

    public Player(WebSocketSession session) {
        this.session = session;
        this.id = session.getId();
        this.status = Status.NONE;
    }

    public void broadcast(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebSocketSession getSession() {
        return session;
    }

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getWordCounter() {
        return wordCounter;
    }

    public void setWordCounter(int wordCounter) {
        this.wordCounter = wordCounter;
    }

    public void incrementCounter() {
        wordCounter++;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeoutTimer getTimeoutTimer() {
        return timeoutTimer;
    }

    public void setTimeoutTimer(TimeoutTimer timeoutTimer) {
        this.timeoutTimer = timeoutTimer;
    }
}
