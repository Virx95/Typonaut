package ee.technest.typonaut.modal;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public class Typer {

    private final WebSocketSession session;
    private final long id;
    private Status status;
    private Player player;
    private String word;
    private List<String> words;
    private int wordCounter = 0;
    private Typer opponent;

    public Typer(WebSocketSession session, Player player) {
        this.session = session;
        this.id = player.getId();
        this.player = player;
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

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
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

    public Typer getOpponent() {
        return opponent;
    }

    public void setOpponent(Typer opponent) {
        this.opponent = opponent;
    }
}
