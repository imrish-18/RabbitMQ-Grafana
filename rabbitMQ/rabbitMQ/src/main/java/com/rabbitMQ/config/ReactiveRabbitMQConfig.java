package com.rabbitMQ.config;

import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.rabbitmq.*;

@Configuration
public class ReactiveRabbitMQConfig {


        @Value("${rabbitmq.host}")
        private String host;

        @Value("${rabbitmq.port}")
        private int port;

        @Value("${rabbitmq.username}")
        private String username;

        @Value("${rabbitmq.password}")
        private String password;

    public static final String EXCHANGE_NAME = "logs.exchange";
    public static final String ERROR_QUEUE = "error.queue";
    public static final String INFO_QUEUE = "info.queue";

        @Bean
        public ConnectionFactory connectionFactory() {
            com.rabbitmq.client.ConnectionFactory cf = new com.rabbitmq.client.ConnectionFactory();
            cf.setHost(host);
            cf.setPort(port);
            cf.setUsername(username);
            cf.setPassword(password);
            return cf;
        }

        @Bean
        public SenderOptions senderOptions(ConnectionFactory connectionFactory) {
            return new SenderOptions().connectionFactory(connectionFactory);
        }

        @Bean
        public Sender sender(SenderOptions options) {
            return RabbitFlux.createSender(options);
        }

        @Bean
        public ReceiverOptions receiverOptions(ConnectionFactory connectionFactory) {
            return new ReceiverOptions().connectionFactory(connectionFactory);
        }

        @Bean
        public Receiver receiver(ReceiverOptions options) {
            return RabbitFlux.createReceiver(options);
        }

    }

