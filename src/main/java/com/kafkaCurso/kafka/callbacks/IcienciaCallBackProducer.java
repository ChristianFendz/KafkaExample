package com.kafkaCurso.kafka.callbacks;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla que el mensaje fue enviado con exito y que no exista error.
 * 
 * Se puede utilizar para realziar alguna accion cuando se complete el mensaje
 * 
 * @author christian
 *
 */
public class IcienciaCallBackProducer {

	public static final Logger log = LoggerFactory.getLogger(IcienciaCallBackProducer.class);

	public static void main(String... args) throws Exception {
		long startime = System.currentTimeMillis();
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.86:9092");
		props.put("acks", "1");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("linger.ms", 6);

		String msm = "";
		try (Producer<String, String> producer = new KafkaProducer<>(props);) {
			for (int i = 0; i < 1000; i++) {
				msm = "TEST3-" + i;
				// Utilizando clase anonima
//				producer.send(new ProducerRecord<>("TutorialTopic", String.valueOf(i), msm), new Callback() {
//					@Override
//					public void onCompletion(RecordMetadata metadata, Exception exception) {
//						if(exception != null ) {
//							log.info("There was an error {}",exception.getMessage()	);
//						}
//						log.info("Offset = {}, partition = {}, topic = {} ", metadata.offset(),metadata.partition(), metadata.topic());
//					}
//				});

				// utilizando lambdas
				producer.send(new ProducerRecord<>("TutorialTopic", String.valueOf(i), msm), (metadata, exception) -> {
					if (exception != null) {
						log.info("There was an error {}", exception.getMessage());
					}
					log.info("Offset = {}, partition = {}, topic = {} ", metadata.offset(), metadata.partition(),
							metadata.topic());
				});

			}
			producer.flush();
		}
		log.info("Tiempo en procesos {}", (System.currentTimeMillis() - startime));
	}
}
