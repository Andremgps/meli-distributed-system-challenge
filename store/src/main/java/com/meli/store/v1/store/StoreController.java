package com.meli.store.v1.store;

import com.meli.store.v1.store.dto.StoreResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/stores")
@Tag(name = "/v1/stores", description = "Endpoints for store management")
@CrossOrigin(origins = "*")
public class StoreController {

    private StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //poderia ser paginado com Pageable
    @GetMapping
    @Operation(summary = "Get all active stores ordered by name")
    public ResponseEntity<List<StoreResponseDTO>> getAllActiveStores() {
        List<StoreResponseDTO> storeResponseDTO = storeService.getAllActiveStores();
        return ResponseEntity.ok(storeResponseDTO);
    }

    @GetMapping("/storeId")
    @Operation(summary = "Get store details by ID")
    public ResponseEntity<StoreResponseDTO> getStoreById(@PathVariable("storeId") Long storeId) {
        StoreResponseDTO storeResponseDTO = storeService.getStoreById(storeId);
        return ResponseEntity.ok(storeResponseDTO);
    }

}
