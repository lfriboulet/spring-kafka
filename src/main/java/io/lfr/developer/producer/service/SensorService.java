package io.lfr.developer.producer.service;

import io.lfr.developer.models.ConsumptionInformation;
import io.lfr.developer.models.HardwareConfiguration;
import io.lfr.developer.models.SensorEvent;
import io.lfr.developer.producer.config.TopicConfig;
import io.lfr.developer.producer.record.SensorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class SensorService {

    final private KafkaTemplate<String, SensorEvent> kafkaTemplate;
    final private TopicConfig topicConfig;
    final private Logger LOG = LoggerFactory.getLogger(getClass());

    public SensorService(KafkaTemplate<String, SensorEvent> kafkaTemplate, TopicConfig topicConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicConfig = topicConfig;
    }

    public void publish(SensorRequest request) {
        String recordKey = request.serialNumber();
        SensorEvent recordValue = this.produceSensorEvent(request);
        try {
            ListenableFuture<SendResult<String, SensorEvent>> future = this.kafkaTemplate.send(this.topicConfig.getName(), recordKey, recordValue);
            future.addCallback(new KafkaSendCallback<>() {

                @Override
                public void onSuccess(SendResult<String, SensorEvent> result) {
                    handleSuccess(result);
                }

                @Override
                public void onFailure(KafkaProducerException e) {
                    handleFailure(e);
                }
            });

        } catch (KafkaException e) {
            LOG.error(e.getLocalizedMessage());
            // todo implementer une persistence des données dans une db pour éviter la perte de données
        }
    }

    private SensorEvent produceSensorEvent(SensorRequest request) {
        HardwareConfiguration hardware = HardwareConfiguration.newBuilder()
                .setCpu(request.cpu())
                .setDisk(request.disk())
                .setMemory(request.memory())
                .build();

        ConsumptionInformation consumption = ConsumptionInformation.newBuilder()
                .setCpuUsage(request.cpuUsage())
                .setMemoryUsage(request.memoryUsage())
                .setDiskSpaceUsed(request.diskspaceUsed())
                .build();

        return SensorEvent.newBuilder()
                .setTimestamp(request.timestamp())
                .setSerialNumber(request.serialNumber())
                .setHardware(hardware)
                .setConsumption(consumption)
                .build();
    }

    private void handleSuccess(SendResult<String, SensorEvent> result) {
        LOG.info("Sensor event with serial number {} received in topic {} with offset {} in partition {}",
                result.getProducerRecord().key(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition());
    }

    private void handleFailure(KafkaProducerException e) {
        LOG.error("Sensor event with serial number {} cannot be processed caused by {}", e.getFailedProducerRecord().key(), e.getMessage());
        // todo implementer une persistence des données dans une db pour éviter la perte de données
    }

}
