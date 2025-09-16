package com.meli.store.v1.storeinventory.dto;

import com.meli.store.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String sku;
    private String category;
    private Double price;

    public ProductResponseDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.sku = product.getSku();
        this.category = product.getCategory();
        this.price = product.getPrice();
    }
}
