package com.haddouti.springeco.samples.common.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ServiceConfigApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ServiceConfigApplication.class, args);
	}
}
