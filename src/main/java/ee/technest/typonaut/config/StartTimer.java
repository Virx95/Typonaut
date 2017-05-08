package ee.technest.typonaut.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.technest.typonaut.json.JsonConverter;
import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Player;

import java.util.TimerTask;

public class StartTimer extends TimerTask {

    private Player playerOne;
    private Player playerTwo;
    private int countDown = 3;

    public StartTimer(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    @Override
    public void run() {

        try {
            playerOne.broadcast(JsonConverter.getCountDown(countDown));
            playerTwo.broadcast(JsonConverter.getCountDown(countDown));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (countDown == 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long startTime = System.currentTimeMillis();
            playerOne.setStatus(Status.PLAYING);
            playerTwo.setStatus(Status.PLAYING);
            playerOne.setStartTime(startTime);
            playerTwo.setStartTime(startTime);
            playerOne.broadcast(JsonConverter.getWordGuess(playerOne.getWord()));
            playerTwo.broadcast(JsonConverter.getWordGuess(playerTwo.getWord()));
            cancel();
        } else {
            countDown--;
        }
    }
}
