package com.rabbitMQ.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitMQ.entity.User;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

import static com.rabbitMQ.config.ReactiveRabbitMQConfig.ERROR_QUEUE;
import static com.rabbitMQ.config.ReactiveRabbitMQConfig.INFO_QUEUE;

@Component
public class MessageConsumer {

    @Autowired
    private  Receiver receiver;

    @Autowired
    private ObjectMapper objectMapper;

    public MessageConsumer(Receiver receiver) {
        receiver.consumeAutoAck("reactive-queue")
                .map(Delivery::getBody)
                .map(String::new)
                .doOnNext(msg -> System.out.println("Received: message is " +
                        " " + msg))
                .subscribe();
    }

    @PostConstruct
    public void consumeMessages() {
        receiver.consumeAutoAck(INFO_QUEUE)
                .subscribe(msg -> System.out.println("ℹ️ INFO: " + new String(msg.getBody())));

        receiver.consumeAutoAck(ERROR_QUEUE)
                .subscribe(msg -> System.out.println("❌ ERROR: " + new String(msg.getBody())));
    }

    @PostConstruct
    public void startConsuming() {
        receiver.consumeAutoAck(INFO_QUEUE)
                .map(delivery -> {
                    try {
                        return objectMapper.readValue(delivery.getBody(), User.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe(user -> System.out.println("👤 Received User: " + user));
    }
}

