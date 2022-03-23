package io.lfr.developer.producer;

import io.lfr.developer.producer.config.TopicConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
public class SpringKafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaProducerApplication.class, args);
	}

/*	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> {
			ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("iot-sensor", "Hello kafka");
			future.addCallback( result -> {
				assert result != null;
				System.out.println(result.getRecordMetadata().offset());
			}, (KafkaFailureCallback<String , String>) ex -> {
				System.out.println(ex.getLocalizedMessage());
			});
		};
	}*/
}
