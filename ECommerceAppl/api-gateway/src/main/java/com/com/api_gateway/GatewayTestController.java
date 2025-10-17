package com.com.api_gateway;

import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayTestController {

    @Observed(name = "gateway.test")
    @GetMapping("/test")
    public String test() {
        return "Gateway OK";
    }
}
