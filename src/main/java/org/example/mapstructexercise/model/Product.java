package org.example.mapstructexercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;          // only stores full description
    private BigDecimal price;            // stored in pence, not pounds
    private Integer stock;
    private String category;
    private String supplier;
    private Boolean active;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(String name, String description, BigDecimal price, Integer stock, String category,
                   String supplier, Boolean active, String image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.supplier = supplier;
        this.active = active;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
