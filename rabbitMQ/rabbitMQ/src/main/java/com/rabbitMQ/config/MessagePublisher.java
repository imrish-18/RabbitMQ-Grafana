package com.rabbitMQ.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitMQ.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import static com.rabbitMQ.config.ReactiveRabbitMQConfig.EXCHANGE_NAME;

@Service
public class MessagePublisher {

    @Autowired
    private  Sender sender;
    private static final String QUEUE = "reactive-queue";

    @Autowired
    private  ObjectMapper objectMapper;

    public MessagePublisher(Sender sender) {
        this.sender = sender;
    }

    public Mono<Void> send(String message) {
        return sender.send(Mono.just(new OutboundMessage("", QUEUE, message.getBytes())))
                .then();
    }

    @PostConstruct
    public void declareQueue() {
        sender.declareQueue(QueueSpecification.queue("reactive-queue"))
                .doOnSuccess(unused -> System.out.println("✅ Queue declared: reactive-queue"))
                .doOnError(err -> System.err.println("❌ Failed to declare queue: " + err.getMessage()))
                .subscribe();  // Trigger the reactive stream
    }

    @PostConstruct
    public void declareQueueInfo() {
        sender.declareQueue(QueueSpecification.queue("info.queue"))
                .doOnSuccess(unused -> System.out.println("✅ Queue declared: reactive-queue"))
                .doOnError(err -> System.err.println("❌ Failed to declare queue: " + err.getMessage()))
                .subscribe();  // Trigger the reactive stream
    }

    @PostConstruct
    public void declareQueueError() {
        sender.declareQueue(QueueSpecification.queue("error.queue"))
                .doOnSuccess(unused -> System.out.println("✅ Queue declared: reactive-queue"))
                .doOnError(err -> System.err.println("❌ Failed to declare queue: " + err.getMessage()))
                .subscribe();  // Trigger the reactive stream
    }

    public void sendMessage(String routingKey, String message) {
        OutboundMessage outboundMessage = new OutboundMessage(
                EXCHANGE_NAME, routingKey, message.getBytes()
        );
        sender.send(Mono.just(outboundMessage)).subscribe();
    }
    @PostConstruct
    public void setup() {
        sender.declareExchange(ExchangeSpecification.exchange("logs.exchange").type("direct")).subscribe();
        sender.declareQueue(QueueSpecification.queue("error.queue")).subscribe();
        sender.declareQueue(QueueSpecification.queue("info.queue")).subscribe();

        sender.bind(BindingSpecification.binding("logs.exchange", "error", "error.queue")).subscribe();
        sender.bind(BindingSpecification.binding("logs.exchange", "info", "info.queue")).subscribe();
    }

    public void sendUser(User user, String routingKey) {
        try {
            byte[] messageBody = objectMapper.writeValueAsBytes(user);
            OutboundMessage message = new OutboundMessage(EXCHANGE_NAME, routingKey, messageBody);
            sender.send(Mono.just(message)).subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

