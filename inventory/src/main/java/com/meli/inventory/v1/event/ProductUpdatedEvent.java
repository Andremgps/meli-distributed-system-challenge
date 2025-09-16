package com.meli.inventory.v1.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductUpdatedEvent {

    private Long id;
    private String name;
    private String sku;
    private String category;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonCreator
    public ProductUpdatedEvent(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("sku") String sku,
            @JsonProperty("category") String category,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
