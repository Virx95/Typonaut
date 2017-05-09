package ee.technest.typonaut;


import ee.technest.typonaut.config.StartTimer;
import ee.technest.typonaut.config.TimeoutTimer;
import ee.technest.typonaut.config.TyponautSession;
import ee.technest.typonaut.json.Json;
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
            playerOneOpt.get().setName(Json.getName(message));
            Optional<Player> playerTwoOpt = TyponautSession.getLookingTyper();
            if (playerTwoOpt.isPresent()) {
                startGame(playerOneOpt.get(), playerTwoOpt.get());
            } else {
                playerOneOpt.get().setStatus(Status.LOOKING);
                playerOneOpt.get().broadcast(Json.getLookingStatus());
            }
        }
    }

    public static void startGame(Player playerOne, Player playerTwo) throws Exception{
        playerOne.setStatus(Status.STARTING);
        playerTwo.setStatus(Status.STARTING);
        playerOne.broadcast(Json.getMessageString("Your opponent is " + playerTwo.getName(), Status.STARTING));
        playerTwo.broadcast(Json.getMessageString("Your opponent is " + playerOne.getName(), Status.STARTING));

        List<String> words = Words.getAllWords();
        int randomNum = ThreadLocalRandom.current().nextInt(0, words.size());
        playerOne.setWord(words.get(randomNum));
        playerTwo.setWord(words.get(randomNum));
        playerOne.setOpponent(playerTwo);
        playerTwo.setOpponent(playerOne);

        startCountDownTimer(playerOne, playerTwo);
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
            if (Json.getWord(message).equals(player.getWord())) {
                player.setEndTime(System.currentTimeMillis());
                if (player.getOpponent().getStatus() == Status.GAME_OVER) {
                    player.broadcast(Json.getMessageString("You lost!", Status.GAME_OVER));
                    player.broadcast(Json.resultId(player.getId()));
                    player.getOpponent().broadcast(Json.resultId(player.getOpponent().getId()));
                    player.getTimeoutTimer().cancel();
                    gameDao.save(player, player.getOpponent());
                } else {
                    player.broadcast(Json.getMessageString("You won!", Status.GAME_OVER));
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
