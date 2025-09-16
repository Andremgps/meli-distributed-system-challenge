package com.meli.store.v1.store;

import com.meli.store.exception.StoreNotFoundException;
import com.meli.store.model.Store;
import com.meli.store.repository.StoreRepository;
import com.meli.store.v1.store.dto.StoreResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreResponseDTO> getAllActiveStores() {
        return storeRepository.findActiveStoresOrderByName()
                .stream()
                .map(StoreResponseDTO::new)
                .toList();
    }

    public StoreResponseDTO getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found for id: " + storeId));
        return new StoreResponseDTO(store);
    }

}
