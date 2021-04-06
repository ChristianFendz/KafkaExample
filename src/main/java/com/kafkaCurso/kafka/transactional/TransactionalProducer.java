package com.kafkaCurso.kafka.transactional;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionalProducer {

	public static final Logger log = LoggerFactory.getLogger(TransactionalProducer.class);

	public static void main(String... args) throws Exception {
		// Determinar el tiempo, camptura el timpo en el instante de ejecucion de la
		// variable
		long startime = System.currentTimeMillis();

		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.86:9092");

		// *** Se debe definir como all en transacciones
		// Soporta tres valores - 0, 1y all.
		/*
		 * 'acks = 0' Con un valor de 0, el productor ni siquiera esperará una respuesta
		 * del corredor. Inmediatamente considera que la escritura fue exitosa en el
		 * momento en que se envía el registro.
		 * 
		 * acks = 1' Con una configuración de 1, el productor considerará que la
		 * escritura fue exitosa cuando el líder reciba el registro.
		 * 
		 * 'acks = all' Cuando se establece en all, el productor considerará que la
		 * escritura se realizó correctamente cuando todas las réplicas sincronizadas
		 * reciban el registro.
		 * 
		 * min.insync.replicas=X
		 * 
		 * Se agrega para establecer el minimo de replicas validas a recibir como
		 * confirmacion
		 */
		props.put("acks", "all");

		props.put("transactional.id", "devs4j-producer");

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
			producer.initTransactions();
			producer.beginTransaction();
			try {
				for (int i = 0; i < 100000; i++) {

					msm = "TEST3-" + i;
					
					producer.send(new ProducerRecord<>("TutorialTopic", "key", msm));
					
					if(i == 5000) {
						throw new Exception ("Unexpected Exception");
					}
				}
				producer.commitTransaction();
				producer.flush();

			} catch (Exception e) {
				log.error("Error", e);
				producer.abortTransaction();
			}
		}
		log.info("Tiempo en procesos {}", (System.currentTimeMillis() - startime));
	}

}
