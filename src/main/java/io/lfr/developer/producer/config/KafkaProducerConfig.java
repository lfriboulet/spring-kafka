package io.lfr.developer.producer.config;

import io.lfr.developer.models.SensorEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.client-id}")
    private String clientId;
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.properties.schema.registry.url}")
    private String schemaregistryUrl;

    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.valueSerializer);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, this.clientId);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, this.retries);
        //properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10);
/*        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
        properties.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 60000);
        properties.put(ProducerConfig.METADATA_MAX_IDLE_CONFIG, 60000);
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 60000);*/
        properties.put("schema.registry.url", this.schemaregistryUrl);
        return properties;
    }

    @Bean
    public ProducerFactory<String, SensorEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(this.producerConfig());
    }

    @Bean
    public KafkaTemplate<String, SensorEvent> kafkaTemplate(ProducerFactory<String, SensorEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
