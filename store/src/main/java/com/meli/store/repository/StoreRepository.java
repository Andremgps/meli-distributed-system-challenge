package com.meli.store.repository;

import com.meli.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByIsActiveTrue();

    @Query("SELECT s FROM Store s WHERE s.isActive = true ORDER BY s.name")
    List<Store> findActiveStoresOrderByName();

}
