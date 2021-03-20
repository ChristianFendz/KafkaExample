package com.kafkaCurso.kafka.multiThread;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursoThreadConsumer extends Thread {
	public static final Logger log = LoggerFactory.getLogger(CursoThreadConsumer.class);

	private final KafkaConsumer<String, String> consumer;

	private static final AtomicBoolean closed = new AtomicBoolean(false);

	public CursoThreadConsumer(KafkaConsumer<String, String> consumer) {
		super();
		this.consumer = consumer;
	}

	@Override
	public void run() {
		consumer.subscribe(Arrays.asList("TutorialTopic"));
		try {

			while (!closed.get()) {
				ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
					log.info("offset = {}, partition = {}, key = {}, value = {}", consumerRecord.offset(),
							consumerRecord.partition(), consumerRecord.key(), consumerRecord.value());
				}
			}

		} catch (WakeupException e) {
			if (!closed.get()) {
				throw e;
			}
		} finally {
			consumer.close();
		}
		super.run();
	}

	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
	}

}
