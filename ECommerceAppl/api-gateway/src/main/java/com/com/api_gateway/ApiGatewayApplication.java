package com.com.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	private static final Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
	public static void main(String[] args) {
		logger.info("Starting API Gateway Application");
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	// ✅ Enables trace context propagation for all downstream calls
	@Bean
	WebClient.Builder webClientBuilder() {
		logger.info("Creating WebClient Builder");
		return WebClient.builder();
	}
}
