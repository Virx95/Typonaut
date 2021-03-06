package ee.technest.typonaut;

import ee.technest.typonaut.config.SimpleWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;


@EnableWebSocket
@SpringBootApplication
public class TyponautApplication implements WebSocketConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TyponautApplication.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(snakeWebSocketHandler(), "/typonaut").withSockJS();
	}

	@Bean
	public WebSocketHandler snakeWebSocketHandler() {
		return new PerConnectionWebSocketHandler(SimpleWebSocketHandler.class);
	}
}
