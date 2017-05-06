package ee.technest.typonaut;


import ee.technest.typonaut.config.StartTimer;
import ee.technest.typonaut.config.TyponautSession;
import ee.technest.typonaut.json.JsonConverter;
import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Typer;
import ee.technest.typonaut.words.Words;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

public class GameEngine {

    public static void tryStartGame(WebSocketSession session, TextMessage message) throws Exception {
        Optional<Typer> playerOne = TyponautSession.getBySessionId(session.getId());
        if (playerOne.isPresent()) {
            playerOne.get().getPlayer().setName(JsonConverter.getName(message));
            Optional<Typer> playerTwo = TyponautSession.getLookingTyper();
            if (playerTwo.isPresent()) {
                playerOne.get().setStatus(Status.STARTING);
                playerTwo.get().setStatus(Status.STARTING);
                playerOne.get().broadcast(JsonConverter.getMessageString("Your opponent is " + playerTwo.get().getPlayer().getName(), Status.STARTING));
                playerTwo.get().broadcast(JsonConverter.getMessageString("Your opponent is " + playerOne.get().getPlayer().getName(), Status.STARTING));

                List<String> words = Words.getAllWords();
                Collections.shuffle(words);
                playerOne.get().setWords(words);
                playerTwo.get().setWords(words);

                startCountDownTimer(playerOne, playerTwo);
            } else {
                playerOne.get().setStatus(Status.LOOKING);
                playerOne.get().broadcast(JsonConverter.getLookingStatus());
            }
        }
    }

    public static void startCountDownTimer(Optional<Typer> playerOne, Optional<Typer> playerTwo) {
        StartTimer task = new StartTimer(playerOne.get(), playerTwo.get());
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    public static void sendWord(WebSocketSession session, TextMessage message) throws Exception {
        Optional<Typer> playerOpt = TyponautSession.getBySessionId(session.getId());
        if (playerOpt.isPresent()) {
            Typer player = playerOpt.get();
            if (JsonConverter.getWord(message).equals(player.getWords().get(player.getWordCounter()))) {
                player.incrementCounter();
                String nextWord = player.getWords().get(player.getWordCounter());
                player.broadcast(JsonConverter.getWordGuess(nextWord));
            }
        }
    }
}
