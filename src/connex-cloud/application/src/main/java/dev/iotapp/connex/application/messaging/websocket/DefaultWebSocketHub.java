package dev.iotapp.connex.application.messaging.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class DefaultWebSocketHub implements WebSocketHub{
    private final Logger log = LoggerFactory.getLogger(DefaultWebSocketHub.class);
    private ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();

    public DefaultWebSocketHub() {
    }

    @Override
    public void sendEvent(String message) {
        this.messageQueue.add(message);
    }

    @Bean
    public Consumer<Message<String>> websocketTest() {
        return message -> {
            log.debug("Websocket message received: ", message.getPayload());
        };
    }

    @Bean
    public Supplier<String> websocketMessages() {
        return () -> {
            String msg = this.messageQueue.poll();
            log.debug("Polling message: " + null == msg? "null" : msg);
            return msg;
        };
    }
}
