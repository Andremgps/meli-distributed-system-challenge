package com.meli.store.v1.store.dto;

import com.meli.store.model.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;

    public StoreResponseDTO(Store store){
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.city = store.getCity();
        this.state = store.getState();
        this.zipCode = store.getZipCode();
        this.phone = store.getPhone();
        this.email = store.getEmail();
    }

}
