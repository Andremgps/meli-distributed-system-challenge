package com.meli.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //address could be made on its own table if needed
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreInventory> inventories;

    private boolean isActive = true;
    private LocalDateTime lastSyncTime;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Store( String name, String address, String city,
                 String state, String zipCode, String phone, String email) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.lastSyncTime = LocalDateTime.now();
    }

    public void updateSyncTime() {
        this.lastSyncTime = LocalDateTime.now();
    }

}
