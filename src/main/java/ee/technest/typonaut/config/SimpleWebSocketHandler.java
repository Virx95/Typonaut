package ee.technest.typonaut.config;

import ee.technest.typonaut.GameEngine;
import ee.technest.typonaut.json.JsonConverter;
import ee.technest.typonaut.modal.Player;
import ee.technest.typonaut.modal.PlayerRepository;
import ee.technest.typonaut.modal.Status;
import ee.technest.typonaut.modal.Typer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class SimpleWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Player player = new Player();
        player = playerRepository.save(player);

        TyponautSession.addTyper(new Typer(session, player));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Status requestStatus = JsonConverter.getRequestType(message.getPayload());
        if (requestStatus == Status.LOOKING) {
            GameEngine.tryStartGame(session, message);
        }
        else if (requestStatus == Status.PLAYING) {
            GameEngine.sendWord(session, message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        System.out.println(status);
    }
}