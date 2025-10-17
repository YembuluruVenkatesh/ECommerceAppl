package com.com.api_gateway;

import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayTracingConfig {

    private static final Logger logger = LoggerFactory.getLogger(GatewayTracingConfig.class);

    @Bean
    public GlobalFilter traceLoggingFilter(Tracer tracer) {
        return (exchange, chain) -> Mono.deferContextual(ctx -> {
            var currentSpan = tracer.currentSpan();
            String traceId = currentSpan != null ? currentSpan.context().traceId() : "N/A";

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("b3", currentSpan != null ? currentSpan.context().traceId() : "")
                    .build();

            logger.info("🌐 Gateway processed traceId: {}", traceId);
            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}
