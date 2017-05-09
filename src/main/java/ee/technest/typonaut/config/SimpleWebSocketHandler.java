package ee.technest.typonaut.config;

import ee.technest.typonaut.GameDao;
import ee.technest.typonaut.GameEngine;
import ee.technest.typonaut.json.Json;
import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;


public class SimpleWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private GameDao gameDao;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        TyponautSession.addTyper(session.getId(), new Player(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Status requestStatus = Json.getRequestType(message.getPayload());
        if (requestStatus == Status.LOOKING) {
            GameEngine.tryStartGame(session, message);
        }
        else if (requestStatus == Status.PLAYING) {
            GameEngine.retrieveWord(session, message, gameDao);
        }
        else if (requestStatus == Status.REMATCH) {
            Optional<Player> playerOpt = TyponautSession.getBySessionId(session.getId());
            if (playerOpt.isPresent() && playerOpt.get().getStatus() == Status.GAME_OVER) {
                Player player = playerOpt.get();
                player.setStatus(Status.REMATCH);
                if (player.getOpponent() != null && player.getOpponent().getStatus() == Status.REMATCH) {
                    GameEngine.startGame(player, player.getOpponent());
                }
                else if (player.getOpponent() != null && player.getOpponent().getStatus() != Status.QUIT) {
                    player.getOpponent().broadcast(Json.getMessageString("Opponent wants a rematch", Status.REMATCH));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        Optional<Player> playerOpt = TyponautSession.getBySessionId(session.getId());
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            if (player.getOpponent() != null && player.getOpponent().getStatus() != Status.QUIT) {
                player.getOpponent().broadcast(Json.getMessageString("Opponent left", Status.QUIT));
            }
            player.setStatus(Status.QUIT);
        }
        TyponautSession.removeTyper(session.getId());
        System.out.println(status);
    }
}