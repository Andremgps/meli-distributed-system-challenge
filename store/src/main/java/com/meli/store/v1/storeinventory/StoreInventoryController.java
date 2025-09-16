package com.meli.store.v1.storeinventory;

import com.meli.store.v1.storeinventory.dto.StoreInventoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stores/inventories")
@Tag(name = "/v1/stores/inventories", description = "Endpoints for store inventory management")
@CrossOrigin(origins = "*")
public class StoreInventoryController {

    private final StoreInventoryService storeInventoryService;

    public StoreInventoryController(StoreInventoryService storeInventoryService) {
        this.storeInventoryService = storeInventoryService;
    }

    @GetMapping
    @Operation(summary = "Get inventory details for a specific product in a specific store")
    public ResponseEntity<StoreInventoryResponseDTO> getInventoryByStoreId(
            @RequestParam("productId") Long productId,
            @RequestParam("storeId") Long storeId
    ) {
        StoreInventoryResponseDTO storeInventoryResponseDTO = storeInventoryService.getProductAvailability(productId, storeId);
        return ResponseEntity.ok(storeInventoryResponseDTO);
    }

}
