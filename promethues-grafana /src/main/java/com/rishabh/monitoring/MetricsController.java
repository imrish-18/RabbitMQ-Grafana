package com.rishabh.monitoring;


import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
public class MetricsController {

    private final MeterRegistry registry;

    public MetricsController(MeterRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/custom-metric")
    public String triggerCustomMetric() {
        registry.counter("my_custom_counter").increment();
        return "Custom metric increased";
    }
}

