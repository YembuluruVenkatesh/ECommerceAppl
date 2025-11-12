package com.ecom.ProductService.service;

import com.ecommerce.dto.OrderEvent;
import com.ecom.ProductService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventListener {

    private final ProductRepository productRepository;

    @KafkaListener(topics = "order-created", groupId = "product-group")
    public void consumeOrderCreated(OrderEvent event) {
        System.out.println("📥 Received Kafka Event: " + event);

        // ✅ Extract data from OrderDto inside the event
        Long productId = event.getOrder().getProductId();
        int quantity = event.getOrder().getQuantity();

        if (productId == null) {
            System.err.println("❌ Product ID is null in event: " + event);
            return;
        }

        productRepository.findById(productId).ifPresent(product -> {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            System.out.println("📦 Updated product stock for ID " + productId);
        });
    }
}
