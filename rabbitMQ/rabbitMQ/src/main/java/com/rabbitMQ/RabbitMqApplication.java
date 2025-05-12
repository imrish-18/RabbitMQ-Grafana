package com.rabbitMQ;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.rabbitmq.QueueSpecification;

@SpringBootApplication
public class RabbitMqApplication {

	private static final Logger log = LoggerFactory.getLogger(RabbitMqApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(RabbitMqApplication.class, args);
		log.info("this is application for rabbitMq ...");
	}


}
