package dev.iotapp.connex.application.messaging.websocket;

import com.jayway.jsonpath.DocumentContext;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import dev.iotapp.connex.application.utils.Json;
import dev.iotapp.connex.application.utils.JsonEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

@Configuration
public class DefaultWebSocketHub implements WebSocketHub{
    private final Logger log = LoggerFactory.getLogger(DefaultWebSocketHub.class);
    private ConcurrentLinkedQueue<Message<String>> messageQueue = new ConcurrentLinkedQueue<>();
    private Map<String, Map<String, Map<String, String>>> subscriptions = new LinkedHashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public DefaultWebSocketHub(ObjectMapper om) {
        this.objectMapper = om;
    }

    @Override
    public void sendEvent(String message) {
        DocumentContext ctx = JsonPath.parse(message);
        Object json = ctx.json();
        if(json instanceof JSONArray) {
            this.subscriptions.forEach((sessionId, telemetries) -> {
                telemetries.forEach((name, datas) -> {
                    datas.forEach((key, value) -> {
                        String query = String.format("$.[?(@.%s=='%s')]", key, value);
                        JSONArray results = ctx.read(query);
                        if(null != results && results.size() > 0) {
                            Message<String> msg = MessageBuilder.withPayload(results.toJSONString()).setHeader("sessionId", sessionId).build();
                            this.messageQueue.add(msg);
                        }
                    });

                });

            });
        }
        
    }

    @Bean
    public Consumer<Message<String>> websocketTest() {
        return message -> {
            log.debug("Websocket message received: ", message.getPayload());

            //JsonNode msg = Json.read(message.getPayload());
            String sessionId = (String)message.getHeaders().get("sessionId");
            WebsocketMessage m = WebsocketMessage.fromJson(message.getPayload());
            handleClientMessage(sessionId, m);
            //WebsocketMessage wsMsg = message.getPayload();
            //System.out.println(wsMsg.getAction());
        };
    }

    @Bean
    public Supplier<Message<String>> websocketMessages() {
        return () -> {
            Message<String> msg = this.messageQueue.poll();
            return msg;
        };
    }

    private void handleClientMessage(String sessionId, WebsocketMessage msg) {
        if(null == msg)
            return;

        switch (msg.getAction()) {
            case "subscribeTelemetry": {
                Map<String, Map<String, String>> tels =  (Map<String, Map<String, String>>)msg.getData().get("telemetries");

                if (tels.size() <= 0)
                    break;

                this.subscriptions.put(sessionId, tels);
            }break;
            
            case "closeSession": {
                this.subscriptions.remove(sessionId);
            }
            default: 
                break;
        }
    }
}
