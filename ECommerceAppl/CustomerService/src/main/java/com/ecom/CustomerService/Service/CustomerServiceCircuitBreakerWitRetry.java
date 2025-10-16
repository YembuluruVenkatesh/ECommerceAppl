package com.ecom.CustomerService.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerServiceCircuitBreakerWitRetry {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceCircuitBreakerWitRetry.class);
    @CircuitBreaker(name = "customerServiceCircuitBreaker", fallbackMethod = "fallbackGetCustomer")
    @Retry(name = "customerServiceRetry")
    public String getCustomerDetails(Long customerId) {
        logger.info(" CustomerServiceCircuitBreakerWitRetry Fetching customer details for ID: " + customerId);
        // Simulate remote call or failure
        if (Math.random() > 0.7) {
            logger.info(" CustomerServiceCircuitBreakerWitRetry Simulated Service Failure!");
            throw new RuntimeException("Simulated Service Failure!");
        }
        return "✅ Customer details for ID: " + customerId;
    }

    // This fallback is automatically called when retries fail or circuit is open
    public String fallbackGetCustomer(Long customerId, Throwable t) {
        logger.info(" fallbackGetCustomer Fetching customer details for ID: " + customerId);
        return "⚠️ Customer service is temporarily unavailable. Please try again later.";
    }
}
