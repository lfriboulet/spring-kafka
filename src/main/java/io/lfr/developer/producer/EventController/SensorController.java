package io.lfr.developer.producer.EventController;

import io.lfr.developer.producer.record.SensorRequest;
import io.lfr.developer.producer.service.SensorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sensor")
public class SensorController {

    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @PostMapping
    public void publish(@RequestBody SensorRequest request) {
        this.service.publish(request);
    }

}
