package com.poc.customerplatform.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    private final MeterRegistry meterRegistry;
    private Counter customerCreatedCounter;
    private Counter customerDeletedCounter;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        initializeMetrics();

    }

    private void initializeMetrics() {
        customerCreatedCounter = meterRegistry.counter("customers.created");
        customerDeletedCounter = meterRegistry.counter("customers.deleted");
    }

    public void incrementCustomerCreatedCounter() {
        customerCreatedCounter.increment();
    }
    public void incrementCustomerDeletedCounter(){ customerDeletedCounter.increment(); }
}
