//By Guomin Huang @16/10/2021

package io.devplus.springcloudstreambinderwebsocket.provisioning;

import org.springframework.cloud.stream.provisioning.ProducerDestination;

public class WebSocketProducerDestination implements ProducerDestination {
    private final String destination;

    public WebSocketProducerDestination(String destination) {
        this.destination = destination;
    }
    @Override
    public String getName() {
        return this.destination;
    }

    @Override
    public String getNameForPartition(int partition) {
        throw new UnsupportedOperationException("Partitioning is not supported in Web Socket.");
    }
}
