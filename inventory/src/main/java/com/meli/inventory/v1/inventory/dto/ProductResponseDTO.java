package com.meli.inventory.v1.inventory.dto;

import com.meli.inventory.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String sku;
    private String category;
    private BigDecimal price;

    public ProductResponseDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.sku = product.getSku();
        this.category = product.getCategory();
        this.price = product.getPrice();
    }

}
