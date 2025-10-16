package com.ecom.OrderService.Controller;

import com.ecom.OrderService.Service.OrderServiceCircuitBreakerWitRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order/circuit-breaker")
public class OrderControllerCircuitBreaker {
    private static final Logger logger = LoggerFactory.getLogger(OrderControllerCircuitBreaker.class);
    private final OrderServiceCircuitBreakerWitRetry orderService;

    public OrderControllerCircuitBreaker(OrderServiceCircuitBreakerWitRetry orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}/details")
    public String getOrderDetails(@PathVariable Long id) {
        logger.info("Fetching order details with circuit breaker for ID: {}", id);
        return orderService.getOrderDetails(id);
    }
}
