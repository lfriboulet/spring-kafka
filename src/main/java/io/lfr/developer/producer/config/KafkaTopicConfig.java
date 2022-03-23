package io.lfr.developer.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    final private TopicConfig topicConfig;

    public KafkaTopicConfig(TopicConfig topicConfig) {
        this.topicConfig = topicConfig;
    }

    @Bean
    public NewTopic iotSensor() {
        return TopicBuilder.name(topicConfig.getName())
                .partitions(topicConfig.getDefaultPartitions())
                .replicas(topicConfig.getDefaultPartitions())
                .build();
    }

}
