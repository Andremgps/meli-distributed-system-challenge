package com.meli.store.v1.storeinventory.dto;

import com.meli.store.model.StoreInventory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInventoryResponseDTO {

    private Long storeId;
    private ProductResponseDTO product;
    private int totalStock;
    private int availableStock;
    private int reservedStock;

    public StoreInventoryResponseDTO(StoreInventory storeInventory){
        this.storeId = storeInventory.getId();
        this.product = new ProductResponseDTO(storeInventory.getProduct());
        this.totalStock = storeInventory.getTotalStock();
        this.availableStock = storeInventory.getAvailableStock();
        this.reservedStock = storeInventory.getReservedStock();
    }

}
