package com.meli.inventory.v1.reservation.dto;

import com.meli.inventory.v1.util.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequestDTO {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Store ID is required")
    private Long storeId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Order ID is required")
    private String orderId;

    private Long reservationTtl = Constants.DEFAULT_RESERVATION_EXPIRATION_SECONDS;

}
