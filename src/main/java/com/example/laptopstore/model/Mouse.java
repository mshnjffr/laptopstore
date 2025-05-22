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
@Table(name = "mice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mouse {
    
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
    
    private String connectionType; // Wired, Wireless, Bluetooth, etc.
    
    private Integer dpi; // Dots Per Inch (sensor resolution)
    
    private String sensor; // Sensor model/type
    
    private Integer pollingRate; // in Hz
    
    private Integer buttons; // Number of buttons
    
    private Boolean rgbLighting;
    
    private String batteryLife; // For wireless mice
    
    private String weight; // in grams
    
    private String dimensions; // in mm
    
    private String color;
    
    private Boolean ergonomic;
    
    private String gripType; // Palm, Claw, Fingertip
    
    private String imagePath;
    
    private Integer stockQuantity;
    
    private Boolean isAvailable = true;
}