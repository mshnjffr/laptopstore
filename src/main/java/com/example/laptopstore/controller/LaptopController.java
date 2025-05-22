package com.example.laptopstore.controller;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.service.LaptopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {

    private final LaptopService laptopService;

    @Autowired
    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping
    public ResponseEntity<List<Laptop>> getAllLaptops() {
        return ResponseEntity.ok(laptopService.getAllLaptops());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Laptop>> getAllAvailableLaptops() {
        return ResponseEntity.ok(laptopService.getAllAvailableLaptops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laptop> getLaptopById(@PathVariable Long id) {
        return ResponseEntity.ok(laptopService.getLaptopById(id));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Laptop>> getLaptopsByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(laptopService.getLaptopsByBrand(brand));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Laptop>> getLaptopsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(laptopService.getLaptopsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Laptop>> searchLaptops(@RequestParam String keyword) {
        return ResponseEntity.ok(laptopService.searchLaptops(keyword));
    }

    @PostMapping
    public ResponseEntity<Laptop> createLaptop(@Valid @RequestBody Laptop laptop) {
        return new ResponseEntity<>(laptopService.saveLaptop(laptop), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laptop> updateLaptop(
            @PathVariable Long id,
            @Valid @RequestBody Laptop laptopDetails) {
        return ResponseEntity.ok(laptopService.updateLaptop(id, laptopDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaptop(@PathVariable Long id) {
        laptopService.deleteLaptop(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Laptop> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(laptopService.updateStock(id, quantity));
    }
}