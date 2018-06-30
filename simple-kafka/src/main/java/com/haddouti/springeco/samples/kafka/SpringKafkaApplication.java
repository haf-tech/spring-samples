package com.haddouti.springeco.samples.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SpringKafkaApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}
}
