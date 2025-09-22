package org.example.mapstructexercise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private String title;
    private String summary;              // short description only
    private String price;                // formatted as "£10.99"
    private boolean inStock;             // true if stock > 0
    private String category;
    private String imageUrl;
    private String availability;
}
