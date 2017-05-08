package ee.technest.typonaut.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.technest.typonaut.GameDao;
import ee.technest.typonaut.json.Json;
import ee.technest.typonaut.modal.Player;
import ee.technest.typonaut.modal.Status;

import java.util.TimerTask;


public class TimeoutTimer extends TimerTask {

    private Player player;
    private int countDown = 5;
    private GameDao gameDao;

    public TimeoutTimer(Player player, GameDao gameDao) {
        this.player = player;
        player.setTimeoutTimer(this);
        this.gameDao = gameDao;
    }

    @Override
    public void run() {
        if (countDown == 0) {
            try {
                player.broadcast(Json.getMessageString("You lost!", Status.GAME_OVER));
                player.broadcast(Json.resultId(player.getId()));
                player.getOpponent().broadcast(Json.resultId(player.getOpponent().getId()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            player.setStatus(Status.GAME_OVER);
            player.setEndTime(System.currentTimeMillis());
            cancel();
            gameDao.save(player, player.getOpponent());
        } else {
            try {
                player.broadcast(Json.getTimeOut(countDown));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            countDown--;
        }
    }
}
