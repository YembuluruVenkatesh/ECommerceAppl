package com.ecom.OrderService.Service;

import com.ecom.CustomerService.dto.ErrorResponse;
import com.ecom.OrderService.Entity.Customer;
import com.ecom.OrderService.Entity.Order;
import com.ecom.OrderService.Repository.OrderRepository;
import com.ecom.OrderService.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order createOrder(Order order) {
        logger.warn("Creating order: {}", order);
        //String url = "http://localhost:8080/api/customers/" + order.getCustomerId(); - // Old (hardcoded)
        String url = "http://CUSTOMER-SERVICE/api/customers/" + order.getCustomerId(); // New (Eureka service name)
        try {
            ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);
            logger.warn("Response from Customer Service: {}", response.getBody());

            if (response.getBody() == null) {
                throw new ResourceNotFoundException(
                        "Customer with ID " + order.getCustomerId() + " not found. Cannot create order."
                );
            }

        } catch (HttpClientErrorException ex) {
            // Extract only the message from the Customer Service error response
            try {
                ObjectMapper mapper = new ObjectMapper();
                ErrorResponse error = mapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
                throw new ResourceNotFoundException(error.getMessage());
            } catch (JsonProcessingException e) {
                throw new ResourceNotFoundException("Customer not found");
            }
        }

        logger.warn("Customer found. Creating order...");
        return repository.save(order);
    }
}
