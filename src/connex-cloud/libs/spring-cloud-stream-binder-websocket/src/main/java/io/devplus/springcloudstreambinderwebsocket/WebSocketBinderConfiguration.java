//By Guomin Huang @17/10/2021
package io.devplus.springcloudstreambinderwebsocket;

import io.devplus.springcloudstreambinderwebsocket.operation.WebSocketOperation;
import io.devplus.springcloudstreambinderwebsocket.properties.WebSocketProperties;
import io.devplus.springcloudstreambinderwebsocket.provisioning.WebSocketChannelProvisioner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.support.WebSocketHandlerMapping;

@Configuration

//@Import({
//    WebSocketConfiguration.class
//})
public class WebSocketBinderConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public WebSocketChannelProvisioner webSocketChannelProvisioner(WebSocketProperties webSocketProperties) {
        return new WebSocketChannelProvisioner();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketMessageChannelBinder webSocketMessageChannelBinder(WebSocketChannelProvisioner webSocketChannelProvisioner, WebSocketOperation webSocketOperation) {
        return new WebSocketMessageChannelBinder(null, webSocketChannelProvisioner/*, webSocketOperation*/);
    }
}
