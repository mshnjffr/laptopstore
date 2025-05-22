package com.example.laptopstore.repository;

import com.example.laptopstore.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    
    List<Laptop> findByBrand(String brand);
    
    List<Laptop> findByModelContainingIgnoreCase(String model);
    
    List<Laptop> findByPriceLessThanEqual(BigDecimal price);
    
    List<Laptop> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT l FROM Laptop l WHERE l.isAvailable = true")
    List<Laptop> findAllAvailableLaptops();
    
    @Query("SELECT l FROM Laptop l WHERE l.brand = :brand AND l.isAvailable = true")
    List<Laptop> findAvailableLaptopsByBrand(@Param("brand") String brand);
    
    @Query("SELECT l FROM Laptop l WHERE "  +
           "LOWER(l.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.processor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Laptop> searchLaptops(@Param("keyword") String keyword);
    
    @Query("SELECT l FROM Laptop l WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(l.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.processor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:brand IS NULL OR :brand = '' OR l.brand = :brand)")
    List<Laptop> searchLaptopsByKeywordAndBrand(@Param("keyword") String keyword, @Param("brand") String brand);
}