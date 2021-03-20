package com.kafkaCurso.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursoConsumer {
	public static final Logger log = LoggerFactory.getLogger(CursoConsumer.class);

	public static void main(String[] args) {

		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "192.168.1.86:9092");
		// Identificador de consumidor
		props.setProperty("group.id", "devs4j-group");
		// Hace commit a los registros leidos
		props.setProperty("enable.auto.commit", "true");
		// cada que tiempo hace commit
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Arrays.asList("TutorialTopic"));
			while (true) {
				ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
					log.info("offset = {}, partition = {}, key = {}, value = {}", consumerRecord.offset(), consumerRecord.partition(), consumerRecord.key (),
							consumerRecord.value());
				}
			}
		} finally {
			// TODO: handle finally clause
		}

	}
}
