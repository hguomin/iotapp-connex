//By Guomin Huang @17/10/2021
package io.devplus.springcloudstreambinderwebsocket.adapters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.messaging.Message;

import io.devplus.springcloudstreambinderwebsocket.operation.WebSocketOperation;

/**
 * This class handle message from application code, then send it to web socket channel
 * @author Guomin Huang
 */
public class OutboundMessageHandler extends AbstractMessageProducingHandler {
    private final Logger log = LoggerFactory.getLogger(OutboundMessageHandler.class);
    private final WebSocketOperation operation;

    public OutboundMessageHandler(WebSocketOperation operation) {
        this.operation = operation;
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        try {
            this.operation.send(message);
        } catch (IOException e) {
            log.error("Error occurs while sending message to websocket client.", e);
        }
    }
}
