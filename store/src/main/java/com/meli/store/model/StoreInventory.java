package com.meli.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "store_inventory")
public class StoreInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Integer totalStock;
    private Integer availableStock;
    private Integer reservedStock;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public StoreInventory(Store store, Product product, Integer totalStock, Integer availableStock, Integer reservedStock) {
        this.store = store;
        this.product = product;
        this.totalStock = totalStock;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
    }

    public void updateStock(Integer totalStock, Integer availableStock, Integer reservedStock) {
        this.totalStock = totalStock;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
    }

}
