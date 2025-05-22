package com.example.laptopstore.repository;

import com.example.laptopstore.model.Mouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouseRepository extends JpaRepository<Mouse, Long> {
    
    List<Mouse> findByBrand(String brand);
    
    List<Mouse> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);
}