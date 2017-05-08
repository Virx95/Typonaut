package ee.technest.typonaut.modal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id;

    private String p1Id;

    private String p2Id;

    private String p1Name;

    private String p2Name;

    private long p1Time;

    private long p2Time;

    private String word;

    public long getId() {
        return id;
    }

    public String getP1Id() {
        return p1Id;
    }

    public void setP1Id(String p1Id) {
        this.p1Id = p1Id;
    }

    public String getP2Id() {
        return p2Id;
    }

    public void setP2Id(String p2Id) {
        this.p2Id = p2Id;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public long getP1Time() {
        return p1Time;
    }

    public void setP1Time(long p1Time) {
        this.p1Time = p1Time;
    }

    public long getP2Time() {
        return p2Time;
    }

    public void setP2Time(long p2Time) {
        this.p2Time = p2Time;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
