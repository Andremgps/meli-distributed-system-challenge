package com.meli.api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${services.inventory-base-url}")
    private String inventoryServiceUrl;

    @Value("${services.store-base-url}")
    private String storeServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("inventory-service", r -> r
                        .path("/inventory-service/**")
                        .filters(f -> f
                                .stripPrefix(0)
                                .circuitBreaker(config -> config
                                        .setName("inventory-service-cb")
                                        .setFallbackUri("forward:/fallback/inventory-service")
                                )
                        )
                        .uri(inventoryServiceUrl))
                .route("store-service", r -> r
                        .path("/store-service/**")
                        .filters(f -> f
                                .stripPrefix(0)
                                .circuitBreaker(config -> config
                                        .setName("store-service-cb")
                                        .setFallbackUri("forward:/fallback/store-service")
                                )
                        )
                        .uri(storeServiceUrl))
                .build();
    }
}