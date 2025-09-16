package com.meli.inventory.v1.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockUpdateRequestDTO {

    @NotNull(message = "Store ID is required")
    private Long storeId;

    @NotNull(message = "Adjustment value is required")
    private Integer adjustment;

    private String reason;
    private String type;

    public StockUpdateRequestDTO(Long storeId, Integer adjustment, String type,
                              String reason) {
        this.storeId = storeId;
        this.adjustment = adjustment;
        this.type = type;
        this.reason = reason;
    }

}
