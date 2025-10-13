package com.ecom.OrderService.Controller;

import com.ecom.OrderService.Entity.Order;
import com.ecom.OrderService.Repository.OrderRepository;
import com.ecom.OrderService.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    private final OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // ✅ Constructor Injection for both Service and Repository
    public OrderController(OrderService service, OrderRepository orderRepository) {
        this.service = service;
        this.orderRepository = orderRepository;
    }

    // ✅ Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    // ✅ Get specific order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    // ✅ Create new order (with customer validation)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("Creating order: {}", order);
        return ResponseEntity.ok(service.createOrder(order));
    }

    // ✅ Get all orders for a given customer
    @GetMapping("/by-customer")
    public List<Order> getOrdersByCustomerId(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return orderRepository.findByCustomerId(customerId);
        }else {
            return orderRepository.findAll();
        }
    }
}
