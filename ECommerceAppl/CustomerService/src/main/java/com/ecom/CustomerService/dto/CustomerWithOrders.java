package com.ecom.CustomerService.dto;


import com.ecom.CustomerService.Entity.Customer;

import java.util.List;

public class CustomerWithOrders {
    private Customer customer;
    private List<OrderResponse> orders;

    // getters and setters
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<OrderResponse> getOrders() { return orders; }
    public void setOrders(List<OrderResponse> orders) { this.orders = orders; }
}
