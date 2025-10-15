package com.ecom.OrderService.Controller;

import com.ecom.OrderService.Entity.Order;
import com.ecom.OrderService.Repository.OrderRepository;
import com.ecom.OrderService.Service.OrderConfigService;
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
    private final OrderConfigService configService;
    private final OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // ✅ Constructor Injection for both Service and Repository
    public OrderController(OrderService service, OrderRepository orderRepository, OrderConfigService configService) {
        this.service = service;
        this.orderRepository = orderRepository;
        this.configService = configService;
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
    public List<Order> getOrdersByCustomerId(@RequestParam Long customerId) {
        logger.info("Fetching orders for customer ID: {}", customerId);
        if (customerId != null) {
            return orderRepository.findByCustomerId(customerId);
        }else {
            return orderRepository.findAll();
        }
    }
    @GetMapping("/greeting")
    public String greeting() {
        return configService.getGreeting();
    }
}
