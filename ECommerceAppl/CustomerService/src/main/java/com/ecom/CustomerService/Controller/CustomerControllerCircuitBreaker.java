package com.ecom.CustomerService.Controller;

import com.ecom.CustomerService.Service.CustomerServiceCircuitBreakerWitRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/customers/circuit-breaker")
public class CustomerControllerCircuitBreaker {
    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerCircuitBreaker.class);
    private final CustomerServiceCircuitBreakerWitRetry customerService;

    public CustomerControllerCircuitBreaker(CustomerServiceCircuitBreakerWitRetry customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}/details")
    public String getCustomerDetails(@PathVariable Long id) {
        logger.info("Fetching customer details with circuit breaker for ID: {}", id);
        return customerService.getCustomerDetails(id);
    }
}
