package org.example.mapstructexercise.frontend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductCreationForm {
    private String productName;
    private String shortDescription;
    private String fullDescription;
    private double priceInPounds;
    private int stockQuantity;
    private String categoryName;
    private String supplierEmail;
    private boolean isActive;
    private String imageUrl;
}
