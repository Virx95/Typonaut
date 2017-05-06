package ee.technest.typonaut.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.technest.typonaut.json.objects.*;
import ee.technest.typonaut.modal.Status;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

public class JsonConverter {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Status getRequestType(String message) {
        try {
            return mapper.readValue(message, StatusRequest.class).getStatus();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getName(TextMessage message) throws IOException {
        return mapper.readValue(message.getPayload(), LookingRequest.class).getName();
    }

    public static String getWord(TextMessage message) throws IOException {
        return mapper.readValue(message.getPayload(), WordGuess.class).getWord();
    }

    public static String getLookingStatus() throws JsonProcessingException {
        StatusRequest request = new StatusRequest();
        request.setStatus(Status.LOOKING);
        return mapper.writeValueAsString(request);
    }

    public static String getMessageString(String msg, Status status) throws JsonProcessingException {
        MessageAndStatus mesasge = new MessageAndStatus();
        mesasge.setMessage(msg);
        mesasge.setStatus(status);
        return mapper.writeValueAsString(mesasge);
    }

    public static String getCountDown(int counter) throws JsonProcessingException {
        CountDown countDown = new CountDown();
        countDown.setCounter(counter);
        return mapper.writeValueAsString(countDown);
    }

    public static String getWordGuess(String word) {
        WordGuess guess = new WordGuess();
        guess.setWord(word);
        guess.setStatus(Status.PLAYING);
        try {
            return mapper.writeValueAsString(guess);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
