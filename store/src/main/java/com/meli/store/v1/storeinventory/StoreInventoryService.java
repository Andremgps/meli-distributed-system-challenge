package com.meli.store.v1.storeinventory;

import com.meli.shared.events.InventoryUpdatedEvent;
import com.meli.shared.events.ProductUpdatedEvent;
import com.meli.store.exception.StoreNotFoundException;
import com.meli.store.model.Product;
import com.meli.store.model.Store;
import com.meli.store.model.StoreInventory;
import com.meli.store.repository.ProductRepository;
import com.meli.store.repository.StoreInventoryRepository;
import com.meli.store.repository.StoreRepository;
import com.meli.store.v1.storeinventory.dto.StoreInventoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreInventoryService {

    private StoreInventoryRepository storeInventoryRepository;
    private StoreRepository storeRepository;
    private ProductRepository productRepository;

    public StoreInventoryService(StoreInventoryRepository storeInventoryRepository, StoreRepository storeRepository, ProductRepository productRepository) {
        this.storeInventoryRepository = storeInventoryRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    public StoreInventoryResponseDTO getProductAvailability(Long productId, Long storeId) {
        StoreInventory storeInventory = storeInventoryRepository.findByStoreAndProduct(productId, storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store inventory not found for productId: " + productId + " and storeId: " + storeId));
        return new StoreInventoryResponseDTO(storeInventory);
    }

    public void updateLocalInventoryCache(InventoryUpdatedEvent inventoryUpdatedEvent) {
        Optional<StoreInventory> existingInventory = storeInventoryRepository
                .findByStoreAndProduct(inventoryUpdatedEvent.getStoreId(), inventoryUpdatedEvent.getProductId());

        if (existingInventory.isPresent()) {
            StoreInventory inventory = existingInventory.get();
            inventory.updateStock(inventoryUpdatedEvent.getTotalStock(), inventoryUpdatedEvent.getAvailableStock(), inventoryUpdatedEvent.getReservedStock());
            storeInventoryRepository.save(inventory);
        } else {
            Store store = storeRepository.findById(inventoryUpdatedEvent.getStoreId())
                    .orElseThrow(() -> new StoreNotFoundException("Store not found with id: " + inventoryUpdatedEvent.getStoreId()));

            Product product = productRepository.findById(inventoryUpdatedEvent.getProductId())
                    .orElseGet(() -> {
                        ProductUpdatedEvent productUpdatedEvent = inventoryUpdatedEvent.getProductUpdatedEvent();
                        Product p = new Product(
                                productUpdatedEvent.getId(),
                                productUpdatedEvent.getName(),
                                productUpdatedEvent.getSku(),
                                productUpdatedEvent.getCategory(),
                                productUpdatedEvent.getPrice());
                        return productRepository.save(p);
                    });
            StoreInventory newInventory = new StoreInventory(
                    store, product, inventoryUpdatedEvent.getTotalStock(), inventoryUpdatedEvent.getAvailableStock(), inventoryUpdatedEvent.getReservedStock()
            );
            storeInventoryRepository.save(newInventory);
        }
    }

}
