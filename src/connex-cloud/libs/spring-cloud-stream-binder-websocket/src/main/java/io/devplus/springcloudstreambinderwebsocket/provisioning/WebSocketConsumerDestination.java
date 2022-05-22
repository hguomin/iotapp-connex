//By Guomin Huang @16/10/2021

package io.devplus.springcloudstreambinderwebsocket.provisioning;

import org.springframework.cloud.stream.provisioning.ConsumerDestination;

public class WebSocketConsumerDestination implements ConsumerDestination {
    private final String destination;

    public WebSocketConsumerDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getName() {
        return this.destination;
    }
}
