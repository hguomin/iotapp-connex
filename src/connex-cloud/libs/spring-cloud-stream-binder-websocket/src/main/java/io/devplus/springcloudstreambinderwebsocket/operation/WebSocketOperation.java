package io.devplus.springcloudstreambinderwebsocket.operation;

import java.io.IOException;
import java.util.function.Consumer;
import org.springframework.messaging.Message;

public interface WebSocketOperation {
    public void subscribe(Consumer<Message<?>> consumer);
    public void send(Message<?> message) throws IOException;
}
