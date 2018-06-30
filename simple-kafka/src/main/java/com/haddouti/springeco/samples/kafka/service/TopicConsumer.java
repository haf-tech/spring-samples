package com.haddouti.springeco.samples.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * A general Kafka topic consumer.
 * 
 */
@Service
public class TopicConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(TopicConsumer.class);

	@KafkaListener(topics = "${app.kafka.topic.atopic}")
	public void receive(@Payload final String message, @Headers final MessageHeaders headers) {
		LOG.info("received message='{}'", message);

		headers.keySet().forEach(key -> LOG.info("{}: {}", key, headers.get(key)));
	}
}
