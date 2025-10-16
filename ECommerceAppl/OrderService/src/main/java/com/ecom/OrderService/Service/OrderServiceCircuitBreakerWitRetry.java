package com.ecom.OrderService.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceCircuitBreakerWitRetry {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceCircuitBreakerWitRetry.class);
    @CircuitBreaker(name = "orderServiceCircuitBreaker", fallbackMethod = "fallbackGetOrder")
    @Retry(name = "orderServiceRetry")
    public String getOrderDetails(Long orderId) {
        logger.info(" OrderServiceCircuitBreakerWitRetry Fetching Order details for ID: " + orderId);
        // Simulate remote call or failure
        if (Math.random() > 0.7) {
            logger.info(" OrderServiceCircuitBreakerWitRetry Simulated Service Failure!");
            throw new RuntimeException("Simulated Service Failure!");
        }
        return "✅ Order details for ID: " + orderId;
    }

    // This fallback is automatically called when retries fail or circuit is open
    public String fallbackGetOrder(Long orderId, Throwable t) {
        logger.info(" fallbackGetOrder Fetching Order details for ID: " + orderId);
        return "⚠️ Order service is temporarily unavailable. Please try again later.";
    }
}
