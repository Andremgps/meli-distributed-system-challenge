package com.meli.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Long storeId;
    private Integer totalStock;
    private Integer availableStock;
    private Integer reservedStock = 0;
    private Integer reorderLevel;
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Version
    private Long version = 0L;

    public boolean canReserve(int quantity) {
        return availableStock >= quantity;
    }

    public void reserveStock(int quantity) {
        if (!canReserve(quantity)) {
            throw new IllegalStateException("Insufficient stock to reserve");
        }
        this.availableStock -= quantity;
        this.reservedStock += quantity;
    }

    public void releaseStock(int quantity) {
        if (reservedStock < quantity) {
            throw new IllegalStateException("Cannot release more than reserved");
        }
        this.reservedStock -= quantity;
        this.availableStock += quantity;
    }

    public void confirmReservation(int quantity) {
        if (reservedStock < quantity) {
            throw new IllegalStateException("Cannot confirm more than reserved");
        }
        this.reservedStock -= quantity;
        this.totalStock -= quantity;
    }

    public void addStock(int quantity) {
        this.totalStock += quantity;
        this.availableStock += quantity;
    }
}
