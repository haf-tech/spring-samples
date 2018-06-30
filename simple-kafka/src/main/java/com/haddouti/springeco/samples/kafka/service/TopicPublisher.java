package com.haddouti.springeco.samples.kafka.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * A general topic publisher implementation.
 *
 */
@Service
public class TopicPublisher {

	private static final Logger LOG = LoggerFactory.getLogger(TopicPublisher.class);

	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;

	@Value("${app.kafka.topic.a-topic}")
	private String kafkaTopic;

	public void send(final String message) {

		send(kafkaTopic, message);
	}

	public void send(final String topic, final String message) {
		LOG.info("sending message='{}' to topic='{}'", message, topic);

		final ListenableFuture<SendResult<Integer, String>> result = kafkaTemplate.send(topic, message);

		SendResult<Integer, String> sendResult;
		try {
			sendResult = result.get();
			final RecordMetadata metaData = sendResult.getRecordMetadata();

			LOG.info("recordMetadata: {}", metaData);
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("Waiting for result failed.", e);
		}
	}
}
