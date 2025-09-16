package com.meli.inventory.v1.inventory.dto;

import com.meli.inventory.model.Inventory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryResponseDTO {

    private Long inventoryId;
    private ProductResponseDTO product;
    private Long storeId;
    private Integer totalStock;
    private Integer availableStock;
    private Integer reservedStock;
    private Integer reorderLevel;
    private LocalDateTime lastUpdated;
    private Boolean lowStock;

    public InventoryResponseDTO(Inventory inventory){
        this.inventoryId = inventory.getId();
        this.product = new ProductResponseDTO(inventory.getProduct());
        this.storeId = inventory.getStoreId();
        this.totalStock = inventory.getTotalStock();
        this.availableStock = inventory.getAvailableStock();
        this.reservedStock = inventory.getReservedStock();
        this.reorderLevel = inventory.getReorderLevel();
        this.lastUpdated = inventory.getUpdatedAt();
        this.lowStock = inventory.getAvailableStock() <= inventory.getReorderLevel();
    }

}
