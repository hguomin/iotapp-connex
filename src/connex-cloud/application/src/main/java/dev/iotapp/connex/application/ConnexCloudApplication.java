package dev.iotapp.connex.application;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.iotapp.connex.application.messaging.websocket.WebSocketHub;

@SpringBootApplication
public class ConnexCloudApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConnexCloudApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConnexCloudApplication.class, args);
	}

	@Bean
    public Consumer<String> receive(WebSocketHub wsHub) {
        return message -> {
            LOGGER.info(message);
            wsHub.sendEvent(message);
            /*
            Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);
            LOGGER.info("New message received: '{}', partition key: {}, sequence number: {}, offset: {}, enqueued time: {}",
                message.getPayload(),
                message.getHeaders().get(EventHubsHeaders.PARTITION_KEY),
                message.getHeaders().get(EventHubsHeaders.SEQUENCE_NUMBER),
                message.getHeaders().get(EventHubsHeaders.OFFSET),
                message.getHeaders().get(EventHubsHeaders.ENQUEUED_TIME)
            );
            checkpointer.success()
                        .doOnSuccess(success -> LOGGER.info("Message '{}' successfully checkpointed", message.getPayload()))
                        .doOnError(error -> LOGGER.error("Exception found", error))
                        .block();
                        */
        };
    }

    @Bean 
    public ObjectMapper jsonObjectMapper() {
        return new ObjectMapper();
    }
}
