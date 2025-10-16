package com.ecom.CustomerService.Service;

import com.ecom.CustomerService.Entity.Customer;
import com.ecom.CustomerService.Repository.CustomerRepository;
import com.ecom.CustomerService.dto.CustomerWithOrdersDto;
import com.ecom.CustomerService.dto.OrderDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @CircuitBreaker(name = "orderServiceCircuitBreaker", fallbackMethod = "fallbackGetCustomerWithOrders")
    @Retry(name = "orderServiceRetry")
    public CustomerWithOrdersDto getCustomerWithOrders(Long customerId) {
        Customer customer = getCustomerById(customerId);
        logger.info("Fetching customer details with orders for ID: " + customerId);
        // ✅ Ensure OrderService has endpoint /api/orders/by-customer?customerId={id}
        // String url = "http://localhost:8082/api/orders/by-customer?customerId=" + customerId; // Old (hardcoded)
        String url = "http://ORDER-SERVICE/api/orders/by-customer?customerId=" + customerId; // New (Eureka service name)
        ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(url, OrderDto[].class);

        List<OrderDto> orders = response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();

        return new CustomerWithOrdersDto(customer, orders);
    }

    public CustomerWithOrdersDto fallbackGetCustomerWithOrders(Long customerId, Throwable t) {
        Customer customer = getCustomerById(customerId);
        logger.info("⚠️ Fallback: Order Service is unavailable - " + t.getMessage());
        return new CustomerWithOrdersDto(customer, List.of());
    }
}
