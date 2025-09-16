package com.meli.store.v1.listener;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryUpdatedEvent {

    private ProductUpdatedEvent productUpdatedEvent;
    private Long productId;
    private Long storeId;
    private Integer totalStock;
    private Integer availableStock;
    private Integer reservedStock = 0;
    private String reason;
    private String adjustmentType;

    private LocalDateTime timestamp;

    @JsonCreator
    public InventoryUpdatedEvent(
            @JsonProperty("productUpdatedEvent") ProductUpdatedEvent productUpdatedEvent,
            @JsonProperty("storeId") Long storeId,
            @JsonProperty("totalStock") Integer totalStock,
            @JsonProperty("availableStock") Integer availableStock,
            @JsonProperty("reservedStock") Integer reservedStock,
            @JsonProperty("reason") String reason,
            @JsonProperty("eventType") String adjustmentType) {
        this.productUpdatedEvent = productUpdatedEvent;
        this.productId = productUpdatedEvent != null ? productUpdatedEvent.getId() : null;
        this.storeId = storeId;
        this.totalStock = totalStock;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
        this.reason = reason;
        this.adjustmentType = adjustmentType;
        this.timestamp = LocalDateTime.now();
    }

    public InventoryUpdatedEvent(
            @JsonProperty("productId") Long productId,
            @JsonProperty("storeId") Long storeId,
            @JsonProperty("totalStock") Integer totalStock,
            @JsonProperty("availableStock") Integer availableStock,
            @JsonProperty("reservedStock") Integer reservedStock,
            @JsonProperty("eventType") String adjustmentType) {
        this.productId = productId;
        this.storeId = storeId;
        this.totalStock = totalStock;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
        this.adjustmentType = adjustmentType;
        this.timestamp = LocalDateTime.now();
    }

}
