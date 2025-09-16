package com.meli.store.repository;

import com.meli.store.model.StoreInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {

    @Query("SELECT si FROM StoreInventory si WHERE si.store.id = :storeId AND si.product.id = :productId")
    Optional<StoreInventory> findByStoreAndProduct(@Param("storeId") Long storeId,
                                                   @Param("productId") Long productId);

}
