package com.rabbitMQ.config;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Component
public class RabbitInitializer {

    @Autowired
    private  Sender sender;

    @PostConstruct
    public void declareQueue() {
        sender.declareQueue(QueueSpecification.queue("reactive-queue"))
                .doOnSuccess(unused -> System.out.println("✅ Queue declared: reactive-queue"))
                .doOnError(err -> System.err.println("❌ Failed to declare queue: " + err.getMessage()))
                .subscribe();  // Trigger the reactive stream
    }
}

