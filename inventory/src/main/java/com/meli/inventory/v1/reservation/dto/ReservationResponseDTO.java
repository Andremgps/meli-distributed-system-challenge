package com.meli.inventory.v1.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDTO {

    private Long id;
    private Long productId;
    private Long storeId;
    private Integer quantity;
    private LocalDateTime expiresAt;
    private String status;

    public ReservationResponseDTO(Long id, Long productId, int quantity,
                                  Long storeId, LocalDateTime expiresAt, String status) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.storeId = storeId;
        this.expiresAt = expiresAt;
        this.status = status;
    }

}
