package com.ecom.ProductService.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private int quantity;
}
