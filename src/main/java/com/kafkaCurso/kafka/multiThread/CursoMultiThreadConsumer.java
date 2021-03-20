package com.kafkaCurso.kafka.multiThread;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CursoMultiThreadConsumer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "192.168.1.86:9092");
		// Identificador de consumidor
		props.setProperty("group.id", "group2");
		// Hace commit a los registros leidos
		props.setProperty("enable.auto.commit", "true");
		// cada que tiempo hace commit
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		for (int i = 0; i < 5; i++) {
			CursoThreadConsumer consumer = new CursoThreadConsumer(new KafkaConsumer<>(props));
			executor.execute(consumer);
		}
		while (!executor.isTerminated()) ;
	}
}
