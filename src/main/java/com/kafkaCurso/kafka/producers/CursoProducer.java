package com.kafkaCurso.kafka.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursoProducer {

	public static final Logger log = LoggerFactory.getLogger(CursoProducer.class);

	public static void main(String... args) throws Exception {
		long startime = System.currentTimeMillis();
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.86:9092");
		props.put("acks", "1");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// Propiedades para mejorar el performance
		/**
		 * Los batches son agrupados por tiempo, este tiempo esta determinado en
		 * milisegundos (Muy relevante haciendo pruebas de performance).
		 */
		props.put("linger.ms", 6);

		String msm = "";
		try (Producer<String, String> producer = new KafkaProducer<>(props);) {
			for (int i = 0; i < 1000000; i++) {

				msm = "TEST3-" + i;
				// EL metodo get se encarga de decir que sea sincorna los envios de mesanjes
				// .get() quitar para que sea asincrono
				producer.send(new ProducerRecord<>("TutorialTopic", "key", msm));

			}
			producer.flush();
		}
		log.info("Tiempo en procesos {}", (System.currentTimeMillis() - startime));
	}

}

/*
 TEST 1
 	- CANT = 100000
 	- LINGER = 0
 	- TIMEPO = 2197  2202
  */
