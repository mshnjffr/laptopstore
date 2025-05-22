package com.example.laptopstore.service;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.repository.LaptopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LaptopService {
    
    private final LaptopRepository laptopRepository;
    
    @Autowired
    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }
    
    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }
    
    public List<Laptop> getAllAvailableLaptops() {
        return laptopRepository.findAllAvailableLaptops();
    }
    
    public Laptop getLaptopById(Long id) {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Laptop not found with id: " + id));
    }
    
    public List<Laptop> getLaptopsByBrand(String brand) {
        return laptopRepository.findByBrand(brand);
    }
    
    public List<Laptop> getLaptopsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return laptopRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public List<Laptop> searchLaptops(String keyword) {
        return laptopRepository.searchLaptops(keyword);
    }
    
    public List<Laptop> searchLaptops(String keyword, String brand) {
        return laptopRepository.searchLaptopsByKeywordAndBrand(keyword, brand);
    }
    
    public Laptop saveLaptop(Laptop laptop) {
        return laptopRepository.save(laptop);
    }
    
    public Laptop updateLaptop(Long id, Laptop laptopDetails) {
        Laptop laptop = getLaptopById(id);
        
        laptop.setBrand(laptopDetails.getBrand());
        laptop.setModel(laptopDetails.getModel());
        laptop.setDescription(laptopDetails.getDescription());
        laptop.setPrice(laptopDetails.getPrice());
        laptop.setProcessor(laptopDetails.getProcessor());
        laptop.setRam(laptopDetails.getRam());
        laptop.setStorage(laptopDetails.getStorage());
        laptop.setDisplaySize(laptopDetails.getDisplaySize());
        laptop.setResolution(laptopDetails.getResolution());
        laptop.setGraphicsCard(laptopDetails.getGraphicsCard());
        laptop.setOperatingSystem(laptopDetails.getOperatingSystem());
        laptop.setWeight(laptopDetails.getWeight());
        laptop.setDimensions(laptopDetails.getDimensions());
        laptop.setBatteryLife(laptopDetails.getBatteryLife());
        laptop.setColor(laptopDetails.getColor());
        laptop.setImagePath(laptopDetails.getImagePath());
        laptop.setStockQuantity(laptopDetails.getStockQuantity());
        laptop.setIsAvailable(laptopDetails.getIsAvailable());
        
        return laptopRepository.save(laptop);
    }
    
    public void deleteLaptop(Long id) {
        Laptop laptop = getLaptopById(id);
        laptopRepository.delete(laptop);
    }
    
    public Laptop updateStock(Long id, Integer quantity) {
        Laptop laptop = getLaptopById(id);
        laptop.setStockQuantity(quantity);
        if (quantity <= 0) {
            laptop.setIsAvailable(false);
        } else {
            laptop.setIsAvailable(true);
        }
        return laptopRepository.save(laptop);
    }
}