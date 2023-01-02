package io.devplus.springcloudstreambinderwebsocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import io.devplus.springcloudstreambinderwebsocket.operation.WebSocketOperation;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.CloseStatus;

public class WebSocketServer extends TextWebSocketHandler implements WebSocketOperation {
    private final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	private Consumer<Message<?>> consumer;
	private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
        log.debug("Websocket connection established: " + session.getId());
	}

    @Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("Websocket message received: " + message.getPayload());
		Message<String> msg = MessageBuilder.withPayload(message.getPayload())
											.setHeader("sessionId", session.getId())
											.build();
		this.consumer.accept(msg);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        
		sessions.remove(session.getId());
		session.close();
		session = null;
		
        log.debug("Websocket transport error.");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Message<String> msg = MessageBuilder.withPayload("{\"action\":\"closeSession\", \"data\": {}, \"headers\": {}}")
			.setHeader("sessionId", session.getId())
			.build();
		this.consumer.accept(msg);
		
		sessions.remove(session.getId());
		session.close();
		session = null;
        log.debug("Websocket connection closed.");
	}

	@Override
	public void subscribe(Consumer<Message<?>> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void send(Message<?> message) throws IOException{
		WebSocketSession session = this.sessions.get(message.getHeaders().get("sessionId"));
		if (null == session) {
			log.info("No websocket session to send message.");
			return;
		}

		session.sendMessage(new TextMessage((byte[])message.getPayload()));
	}
}
