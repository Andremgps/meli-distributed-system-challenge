package com.meli.inventory.v1.inventory;

import com.meli.inventory.exception.InsufficientStockException;
import com.meli.inventory.exception.InventoryNotFoundException;
import com.meli.inventory.model.Inventory;
import com.meli.inventory.repository.InventoryRepository;
import com.meli.inventory.v1.inventory.dto.InventoryResponseDTO;
import com.meli.inventory.v1.inventory.dto.StockUpdateRequestDTO;
import com.meli.shared.events.InventoryUpdatedEvent;
import com.meli.shared.events.ProductUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;
    private ApplicationEventPublisher eventPublisher;

    public InventoryService(InventoryRepository inventoryRepository, ApplicationEventPublisher eventPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public InventoryResponseDTO getStock(Long productId, Long storeId) {
        Inventory inventory = inventoryRepository.findByProductIdAndStoreId(productId, storeId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found for product: " + productId + " in store: " + storeId));

        return new InventoryResponseDTO(inventory);
    }

    @Transactional
    @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public InventoryResponseDTO updateStock(Long inventoryId, StockUpdateRequestDTO stockUpdateRequestDTO){
        Inventory inventory = inventoryRepository.findByProductIdAndStoreId(inventoryId, stockUpdateRequestDTO.getStoreId())
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found for product: " + inventoryId + " in store: " + stockUpdateRequestDTO.getStoreId()));

        int previousStock = inventory.getAvailableStock();
        if(stockUpdateRequestDTO.getAdjustment() > 0){
            inventory.addStock(stockUpdateRequestDTO.getAdjustment());
        }else{
            int reduction = Math.abs(stockUpdateRequestDTO.getAdjustment());
            if (inventory.getAvailableStock() < reduction) {
                throw new InsufficientStockException(
                        "Cannot reduce stock below available amount. Available: " +
                                inventory.getAvailableStock() + ", Requested reduction: " + reduction);
            }
            inventory.setTotalStock(inventory.getTotalStock() - reduction);
            inventory.setAvailableStock(inventory.getAvailableStock() - reduction);
        }

        inventoryRepository.save(inventory);

        eventPublisher.publishEvent(new InventoryUpdatedEvent(
                new ProductUpdatedEvent(
                        inventory.getProduct().getId(),
                        inventory.getProduct().getName(),
                        inventory.getProduct().getSku(),
                        inventory.getProduct().getCategory(),
                        inventory.getProduct().getPrice(),
                        inventory.getProduct().getCreatedAt(),
                        inventory.getProduct().getUpdatedAt()
                ),
                stockUpdateRequestDTO.getStoreId(),
                inventory.getTotalStock(),
                inventory.getAvailableStock(),
                inventory.getReservedStock(),
                stockUpdateRequestDTO.getReason(),
                stockUpdateRequestDTO.getType()
        ));

        return new InventoryResponseDTO(inventory);
    }

}
