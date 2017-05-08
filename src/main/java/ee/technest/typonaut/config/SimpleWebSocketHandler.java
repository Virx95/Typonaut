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
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        TyponautSession.removeTyper(session.getId());
        System.out.println(status);
    }
}