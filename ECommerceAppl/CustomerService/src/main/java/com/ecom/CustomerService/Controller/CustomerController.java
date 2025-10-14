package com.ecom.CustomerService.Controller;

import com.ecom.CustomerService.Entity.Customer;
import com.ecom.CustomerService.Service.CustomerService;
import com.ecom.CustomerService.dto.CustomerWithOrdersDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // Fetch all customers
    @GetMapping
    public List<Customer> getAll() {
        logger.info("Fetching all customers");
        return service.getAllCustomers();
    }

    // Fetch customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        logger.info("Fetching customer with ID: {}", id);
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    // Create new customer
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        logger.info("Creating customer: {}", customer);
        return ResponseEntity.ok(service.createCustomer(customer));
    }

    // Fetch customer and their orders (merged data)
    @GetMapping("/{id}/details")
    public ResponseEntity<CustomerWithOrdersDto> getCustomerWithOrders(@PathVariable Long id) {
        logger.info("Fetching customer details with orders for ID: {}", id);
        CustomerWithOrdersDto response = service.getCustomerWithOrders(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}/with-orders")
    public ResponseEntity<CustomerWithOrdersDto> getCustomerWithOrdersDtoResponseEntityders(@PathVariable Long customerId) {
        CustomerWithOrdersDto dto = service.getCustomerWithOrders(customerId);
        return ResponseEntity.ok(dto);
    }
}
