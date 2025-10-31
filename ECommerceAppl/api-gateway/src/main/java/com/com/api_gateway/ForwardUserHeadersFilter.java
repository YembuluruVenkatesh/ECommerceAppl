package com.com.api_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class ForwardUserHeadersFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -1; // run early in the filter chain
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .filter(auth -> auth instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .flatMap(jwtAuth -> {
                    Jwt jwt = jwtAuth.getToken();
                    String username = jwt.getClaimAsString("preferred_username");
                    List<String> roles = jwt.getClaimAsStringList("roles");
                    if (roles == null) roles = Collections.emptyList();

                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Name", username != null ? username : "")
                            .header("X-Roles", String.join(",", roles))
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .switchIfEmpty(chain.filter(exchange)); // if not authenticated
    }
}
