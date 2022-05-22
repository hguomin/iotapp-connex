package io.devplus.springcloudstreambinderwebsocket.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.websocket")
public class WebSocketProperties {
    private String endpoint;

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSockJSEndpoint() {
        return this.endpoint + "_sockjs";
    }
}
