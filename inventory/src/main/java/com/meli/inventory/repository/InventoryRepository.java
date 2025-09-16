package com.meli.inventory.repository;

import com.meli.inventory.model.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Inventory> findByProductIdAndStoreId(Long productId, Long storeId);

    @Lock(LockModeType.OPTIMISTIC)
    List<Inventory> findByProductId(Long productId);

    List<Inventory> findByStoreId(Long storeId);

    @Query("SELECT i FROM Inventory i WHERE i.totalStock <= i.reorderLevel")
    List<Inventory> findLowStockItems();

    @Query("SELECT SUM(i.availableStock) FROM Inventory i WHERE i.product.id = :productId")
    Integer getTotalAvailableStock(@Param("productId") Long productId);
}
