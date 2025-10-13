package com.ecom.CustomerService.dto;

import com.ecom.CustomerService.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithOrdersDto {
    private Customer customer;
    private List<OrderDto> orders;
}
