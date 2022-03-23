package io.lfr.developer.producer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "topic-config")
public class TopicConfig {

    private int defaultReplicas;
    private int defaultPartitions;
    private String name;

    public int getDefaultReplicas() {
        return defaultReplicas;
    }

    public void setDefaultReplicas(int defaultReplicas) {
        this.defaultReplicas = defaultReplicas;
    }

    public int getDefaultPartitions() {
        return defaultPartitions;
    }

    public void setDefaultPartitions(int defaultPartitions) {
        this.defaultPartitions = defaultPartitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TopicConfig{" +
                "defaultReplicas=" + defaultReplicas +
                ", defaultPartitions=" + defaultPartitions +
                ", name='" + name + '\'' +
                '}';
    }
}
