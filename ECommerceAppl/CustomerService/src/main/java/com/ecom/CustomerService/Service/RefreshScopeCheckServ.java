package com.ecom.CustomerService.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class RefreshScopeCheckServ {

    @Value("${myapp.greeting}")
    private String hello;

    public String getHello() {
        return hello;
    }
    @Value("${customer.default-name}")
    private String defaultName;

    @Value("${customer.max-orders}")
    private int maxOrders;

    @Value("${customer.greeting}")
    private String greeting;

    public String getDefaultName() {
        return defaultName;
    }

    public int getMaxOrders() {
        return maxOrders;
    }

    public String getGreeting() {
        return greeting;
    }
}
