package com.ecom.CustomerService.Repository;

import com.ecom.CustomerService.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  CustomerRepository extends JpaRepository<Customer, Long> {

}
