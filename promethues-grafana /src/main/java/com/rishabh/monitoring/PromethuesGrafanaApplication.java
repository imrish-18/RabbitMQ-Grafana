package com.rishabh.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PromethuesGrafanaApplication {

	public static void main(String[] args) {

		SpringApplication.run(PromethuesGrafanaApplication.class, args);
		log.info("this is monitoring application for prometheus and grafana..");
	}

}
