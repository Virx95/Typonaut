package ee.technest.typonaut;


import ee.technest.typonaut.config.StartTimer;
import ee.technest.typonaut.config.TimeoutTimer;
import ee.technest.typonaut.config.TyponautSession;
import ee.technest.typonaut.json.JsonConverter;
import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Player;
import ee.technest.typonaut.words.Words;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

public class GameEngine {

    public static void tryStartGame(WebSocketSession session, TextMessage message) throws Exception {
        Optional<Player> playerOneOpt = TyponautSession.getBySessionId(session.getId());
        if (playerOneOpt.isPresent()) {
            playerOneOpt.get().setName(JsonConverter.getName(message));
            Optional<Player> playerTwoOpt = TyponautSession.getLookingTyper();
            if (playerTwoOpt.isPresent()) {
                Player playerOne = playerOneOpt.get();
                Player playerTwo = playerTwoOpt.get();
                playerOne.setStatus(Status.STARTING);
                playerTwo.setStatus(Status.STARTING);
                playerOne.broadcast(JsonConverter.getMessageString("Your opponent is " + playerTwoOpt.get().getName(), Status.STARTING));
                playerTwo.broadcast(JsonConverter.getMessageString("Your opponent is " + playerOne.getName(), Status.STARTING));

                List<String> words = Words.getAllWords();
                int randomNum = ThreadLocalRandom.current().nextInt(0, words.size());
                playerOne.setWord(words.get(randomNum));
                playerTwo.setWord(words.get(randomNum));
                playerOne.setOpponent(playerTwo);
                playerTwo.setOpponent(playerOne);

                startCountDownTimer(playerOne, playerTwo);
            } else {
                playerOneOpt.get().setStatus(Status.LOOKING);
                playerOneOpt.get().broadcast(JsonConverter.getLookingStatus());
            }
        }
    }

    public static void startCountDownTimer(Player playerOne, Player playerTwo) {
        StartTimer task = new StartTimer(playerOne, playerTwo);
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    public static void retrieveWord(WebSocketSession session, TextMessage message, GameDao gameDao) throws Exception {
        Optional<Player> playerOpt = TyponautSession.getBySessionId(session.getId());
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            if (JsonConverter.getWord(message).equals(player.getWord())) {
                player.setEndTime(System.currentTimeMillis());
                if (player.getOpponent().getStatus() == Status.GAME_OVER) {
                    player.broadcast(JsonConverter.getMessageString("You lost!", Status.GAME_OVER));
                    player.getTimeoutTimer().cancel();
                    gameDao.save(player, player.getOpponent());
                } else {
                    player.broadcast(JsonConverter.getMessageString("You won!", Status.GAME_OVER));
                    startTimeOutTimer(player.getOpponent(), gameDao);
                }
                player.setStatus(Status.GAME_OVER);
            }
        }
    }

    public static void startTimeOutTimer(Player player, GameDao gameDao) {
        TimeoutTimer task = new TimeoutTimer(player, gameDao);
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }
}
