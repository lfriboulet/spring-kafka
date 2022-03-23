package io.lfr.developer.producer.record;

import java.time.Instant;

public record SensorRequest(String serialNumber,
                            Instant timestamp,
                            double cpu,
                            int memory,
                            long disk,
                            double cpuUsage,
                            double memoryUsage,
                            long diskspaceUsed) {
}
