package com.rabbitMQ.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void listen(String message) {
        System.out.println("Received: " + message);
        log.info("message has been received ...{} ",message);
    }
}

