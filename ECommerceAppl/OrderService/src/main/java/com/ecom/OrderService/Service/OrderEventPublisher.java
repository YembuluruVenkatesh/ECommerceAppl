package com.ecom.OrderService.Service;

import com.ecommerce.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderCreatedEvent(OrderEvent event) {
        kafkaTemplate.send("order-created", event);
        System.out.println("✅ Published event to Kafka: " + event);
    }
}
