package com.ecom.ProductService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.ecom.ProductService.dto.OrderCreatedEvent;
import com.ecom.ProductService.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderEventListener {

    private final ProductRepository productRepository;

    @KafkaListener(topics = "order-created", groupId = "product-group")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        System.out.println("📥 Received Kafka Event: " + event);
        productRepository.findById(event.getProductId()).ifPresent(product -> {
            product.setStock(product.getStock() - event.getQuantity());
            productRepository.save(product);
            System.out.println("📦 Updated product stock for ID " + event.getProductId());
        });
    }
}


