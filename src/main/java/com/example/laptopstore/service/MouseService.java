package com.example.laptopstore.service;

import com.example.laptopstore.model.Mouse;
import com.example.laptopstore.repository.MouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MouseService {

    private final MouseRepository mouseRepository;
    
    @Autowired
    public MouseService(MouseRepository mouseRepository) {
        this.mouseRepository = mouseRepository;
    }
    
    public List<Mouse> getAllMice() {
        return mouseRepository.findAll();
    }
    
    public Mouse getMouseById(Long id) {
        return mouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mouse not found with id: " + id));
    }
    
    public List<Mouse> searchMice(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return mouseRepository.findAll();
        }
        return mouseRepository.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword);
    }
    
    public Mouse createMouse(Mouse mouse) {
        return mouseRepository.save(mouse);
    }
    
    public Mouse updateMouse(Long id, Mouse mouseDetails) {
        Mouse mouse = getMouseById(id);
        
        mouse.setBrand(mouseDetails.getBrand());
        mouse.setModel(mouseDetails.getModel());
        mouse.setDescription(mouseDetails.getDescription());
        mouse.setPrice(mouseDetails.getPrice());
        mouse.setConnectionType(mouseDetails.getConnectionType());
        mouse.setDpi(mouseDetails.getDpi());
        mouse.setSensor(mouseDetails.getSensor());
        mouse.setPollingRate(mouseDetails.getPollingRate());
        mouse.setButtons(mouseDetails.getButtons());
        mouse.setRgbLighting(mouseDetails.getRgbLighting());
        mouse.setBatteryLife(mouseDetails.getBatteryLife());
        mouse.setWeight(mouseDetails.getWeight());
        mouse.setDimensions(mouseDetails.getDimensions());
        mouse.setColor(mouseDetails.getColor());
        mouse.setErgonomic(mouseDetails.getErgonomic());
        mouse.setGripType(mouseDetails.getGripType());
        mouse.setImagePath(mouseDetails.getImagePath());
        mouse.setStockQuantity(mouseDetails.getStockQuantity());
        mouse.setIsAvailable(mouseDetails.getIsAvailable());
        
        return mouseRepository.save(mouse);
    }
    
    public void deleteMouse(Long id) {
        Mouse mouse = getMouseById(id);
        mouseRepository.delete(mouse);
    }
}