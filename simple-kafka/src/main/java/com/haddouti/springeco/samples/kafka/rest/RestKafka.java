package com.haddouti.springeco.samples.kafka.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haddouti.springeco.samples.kafka.service.TopicPublisher;

@RestController
@RequestMapping(path = "/kafka/v1")
public class RestKafka {

	@Autowired
	private TopicPublisher publisher;

	@GetMapping(path = "send/{topic}/{msg}")
	public String sendMessage(@PathVariable("topic") final String topic, @PathVariable("msg") final String message) {

		publisher.send(topic, message);

		return "OK";
	}
}
