package com.example.laptopstore.controller;

import com.example.laptopstore.model.Mouse;
import com.example.laptopstore.service.MouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/mouse")
public class MouseController {

    private final MouseService mouseService;
    
    @Autowired
    public MouseController(MouseService mouseService) {
        this.mouseService = mouseService;
    }
    
    @GetMapping
    public ResponseEntity<List<Mouse>> getAllMice() {
        return ResponseEntity.ok(mouseService.getAllMice());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mouse> getMouseById(@PathVariable Long id) {
        return ResponseEntity.ok(mouseService.getMouseById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Mouse>> searchMice(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(mouseService.searchMice(keyword));
    }
    
    @PostMapping
    public ResponseEntity<Mouse> createMouse(@Valid @RequestBody Mouse mouse) {
        return new ResponseEntity<>(mouseService.createMouse(mouse), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Mouse> updateMouse(
            @PathVariable Long id,
            @Valid @RequestBody Mouse mouseDetails) {
        return ResponseEntity.ok(mouseService.updateMouse(id, mouseDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMouse(@PathVariable Long id) {
        mouseService.deleteMouse(id);
        return ResponseEntity.noContent().build();
    }
}