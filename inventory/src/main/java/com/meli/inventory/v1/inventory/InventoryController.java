package com.meli.inventory.v1.inventory;

import com.meli.inventory.v1.inventory.dto.InventoryResponseDTO;
import com.meli.inventory.v1.inventory.dto.StockUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/inventories")
@Tag(name = "/v1/inventories", description = "Endpoints for inventory management")
@CrossOrigin(origins = "*")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @Operation(summary = "Get current stock for a product in a specific store")
    public ResponseEntity<InventoryResponseDTO> getStock(
            @RequestParam("productId") Long productId,
            @RequestParam("storeId") Long storeId) {
        InventoryResponseDTO response = inventoryService.getStock(productId, storeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{inventoryId}")
    @Operation(summary = "Update stock for a specific inventory item")
    public ResponseEntity<InventoryResponseDTO> updateStock(
            @PathVariable("inventoryId") Long inventoryId,
            @RequestBody @Valid StockUpdateRequestDTO stockUpdateRequestDTO) {
        InventoryResponseDTO response = inventoryService.updateStock(inventoryId, stockUpdateRequestDTO);
        return ResponseEntity.ok(response);
    }


}
