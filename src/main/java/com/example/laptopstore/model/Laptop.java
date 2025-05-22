package com.example.laptopstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "laptops")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Brand cannot be blank")
    private String brand;
    
    @NotBlank(message = "Model cannot be blank")
    private String model;
    
    private String description;
    
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    private String processor;
    
    private String ram;
    
    private String storage;
    
    private String displaySize;
    
    private String resolution;
    
    private String graphicsCard;
    
    private String operatingSystem;
    
    private String weight;
    
    private String dimensions;
    
    private String batteryLife;
    
    private String color;
    
    private String imagePath;
    
    private Integer stockQuantity;
    
    private Boolean isAvailable = true;
}