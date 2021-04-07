package com.kafkaCurso.kafka.ejercicio;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjercicioProducer {

	public static final Logger log = LoggerFactory.getLogger(EjercicioProducer.class);

	public static void main(String[] args) throws ParseException {

		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.86:9092");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("linger.ms", 6);

		/*
		 * La propiedad max.in.flight.requests.per.connection = 1 preserve el orden de
		 * los mensajes, es recomendable que se configure en 1 Esto hará que solo se
		 * pueda realizar una petición de envío de mensajes al mismo tiempo (su valor
		 * por defecto es 5).
		 * 
		 */
		props.put("max.in.flight.requests.per.connection", 1);
		List<Usuario> listaUsuario = new ArrayList<>();
		listaUsuario.add(new Usuario("1020", "Deposito", new BigDecimal("200"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-25 00:00:00")));
		listaUsuario.add(new Usuario("1020", "Deposito", new BigDecimal("100"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-25 01:00:00")));
		listaUsuario.add(new Usuario("1020", "Deposito", new BigDecimal("200"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-25 02:00:00")));
		listaUsuario.add(new Usuario("1020", "Retiro", new BigDecimal("-300"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-25 03:00:00")));
		listaUsuario.add(new Usuario("1021", "Deposito", new BigDecimal("200"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-25 00:00:00")));
		listaUsuario.add(new Usuario("1021", "Deposito", new BigDecimal("200"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-25 00:00:00")));

		try (Producer<String, String> producer = new KafkaProducer<>(props);) {
			for (Usuario usuario : listaUsuario) {
				producer.send(new ProducerRecord<>("TutorialTopic", usuario.getUsuario(), usuario.toString()));
			}
			producer.flush();
		}
	}
}
