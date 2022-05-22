//By Guomin Huang @17/10/2021
package io.devplus.springcloudstreambinderwebsocket.adapters;

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import io.devplus.springcloudstreambinderwebsocket.operation.WebSocketOperation;

/**
 * This class receive message from web socket channel and then send it to application code
 * @author Guomin Huang
 */
public class InboundMessageProducer extends MessageProducerSupport
{
    private WebSocketOperation webSocketOperation;
    public InboundMessageProducer(WebSocketOperation webSocketOperation) {
        this.webSocketOperation = webSocketOperation;
    }

    @Override
    protected void doStart() {
        this.webSocketOperation.subscribe(this::sendMessage);
    }

    @Override
    protected void doStop() {

    }
}
