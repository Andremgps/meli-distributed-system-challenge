package com.meli.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/inventory-service")
    @PostMapping("/inventory-service")
    public Mono<ResponseEntity<Map<String, Object>>> userServiceFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Inventory Service",
                        "Inventory service is currently unavailable. Please try again later.")));
    }

    @GetMapping("/store-service")
    @PostMapping("/store-service")
    public Mono<ResponseEntity<Map<String, Object>>> orderServiceFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createFallbackResponse("Store Service",
                        "Store service is currently unavailable. Please try again later.")));
    }

    private Map<String, Object> createFallbackResponse(String service, String message) {
        return Map.of(
                "error", "Service Unavailable",
                "service", service,
                "message", message,
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.SERVICE_UNAVAILABLE.value()
        );
    }
}
