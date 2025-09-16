package com.meli.inventory.model;

import com.meli.inventory.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.RESERVED;
    private Long storeId;
    private String customerId;
    private String orderId;
    private int quantity;
    private LocalDateTime expiresAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Reservation(Inventory inventory, Long storeId,
                       String customerId, String orderId, int quantity, LocalDateTime expiresAt) {
        this.inventory = inventory;
        this.storeId = storeId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.expiresAt = expiresAt;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt) && status == ReservationStatus.RESERVED;
    }

    public void confirm() {
        if (status != ReservationStatus.RESERVED) {
            throw new IllegalStateException("Can only confirm reserved items");
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot cancel confirmed reservation");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    public void expire() {
        if (status == ReservationStatus.RESERVED) {
            this.status = ReservationStatus.EXPIRED;
        }
    }
}
