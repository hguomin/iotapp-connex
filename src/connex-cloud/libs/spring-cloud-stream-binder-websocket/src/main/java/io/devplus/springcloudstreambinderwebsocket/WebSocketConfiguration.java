package io.devplus.springcloudstreambinderwebsocket;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import io.devplus.springcloudstreambinderwebsocket.operation.WebSocketOperation;
import io.devplus.springcloudstreambinderwebsocket.properties.WebSocketProperties;

@Configuration
@EnableConfigurationProperties(WebSocketProperties.class)
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    //
    //The WebSocketConfiguration bean will be created twice, one is spring boot context refresh, another is the binder context refresh, 
    //so need to make the WebSocketServer as static bean, and can be subscribed by binder's InboundMessageProducer
    //
    //Other reason is that one websocket handler is enough
    //
    private /*static*/ WebSocketServer server = new WebSocketServer();
    private /*static*/ WebSocketProperties serverProperties;
    private /*static*/ boolean initialized = false;

    public WebSocketConfiguration(WebSocketProperties properties) {
        serverProperties = properties;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //if(!initialized) {
            String endpoint = serverProperties.getEndpoint();
            String sockjsEndpoint = serverProperties.getSockJSEndpoint();
            //default endpoint name
            if (null == endpoint || endpoint.isEmpty()) {
                endpoint = "/ws";
                sockjsEndpoint = "/ws_sockjs";
            }
            registry.addHandler(server, endpoint).setAllowedOriginPatterns("*");
            registry.addHandler(server, sockjsEndpoint).setAllowedOriginPatterns("*").withSockJS();
            //initialized = true;
        //}
    }

    @Bean
    public WebSocketOperation webSocketOperation() {
        //this.server = new WebSocketServer();
        return this.server;
        //return server;
    }
}