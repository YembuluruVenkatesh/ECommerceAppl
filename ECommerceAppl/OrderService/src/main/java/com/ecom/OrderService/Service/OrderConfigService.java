package com.ecom.OrderService.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class OrderConfigService {

    @Value("${myapp.greeting}")
    private String greeting;

    public String getGreeting() {
        return greeting;
    }
}
