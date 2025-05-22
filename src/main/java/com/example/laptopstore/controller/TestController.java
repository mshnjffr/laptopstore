package com.example.laptopstore.controller;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.model.Order;
import com.example.laptopstore.model.OrderItem;
import com.example.laptopstore.model.User;
import com.example.laptopstore.service.LaptopService;
import com.example.laptopstore.service.OrderService;
import com.example.laptopstore.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final LaptopService laptopService;
    private final OrderService orderService;
    private final UserService userService;

    // For demo purposes, assume a logged-in user (id=1)
    private static final Long DEMO_USER_ID = 1L;

    @Autowired
    public TestController(LaptopService laptopService, OrderService orderService, UserService userService) {
        this.laptopService = laptopService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/create-order")
    public ResponseEntity<?> testCreateOrder(@RequestParam("laptopId") Long laptopId, @RequestParam("quantity") Integer quantity) {
        try {
            // Log what we're trying to do
            System.out.println("Creating test order for laptop ID: " + laptopId + ", quantity: " + quantity);
            
            // Get the user
            User user = userService.getUserById(DEMO_USER_ID);
            System.out.println("User: " + user);
            
            // Get the laptop
            Laptop laptop = laptopService.getLaptopById(laptopId);
            System.out.println("Laptop: " + laptop);
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setLaptop(laptop);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(laptop.getPrice());
            orderItem.setSubtotal(laptop.getPrice().multiply(BigDecimal.valueOf(quantity)));
            
            List<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            
            // Create the order
            Order order = orderService.createOrder(
                user.getId(),
                orderItems,
                "Test Address",
                "Test Payment"
            );
            
            // Return the created order
            return ResponseEntity.ok().body(order);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating test order: " + e.getMessage());
        }
    }
    
    // Add another test endpoint with direct order creation to avoid any JSON parsing issues
    @PostMapping("/direct-order")
    public ResponseEntity<?> createOrderDirect() {
        try {
            // Get user with ID 1
            User user = userService.getUserById(DEMO_USER_ID);
            
            // Get first available laptop
            List<Laptop> laptops = laptopService.getAllLaptops();
            if (laptops.isEmpty()) {
                return ResponseEntity.badRequest().body("No laptops available");
            }
            
            Laptop laptop = laptops.get(0);
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setLaptop(laptop);
            orderItem.setQuantity(1);
            orderItem.setUnitPrice(laptop.getPrice());
            orderItem.setSubtotal(laptop.getPrice());
            
            List<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            
            // Create the order
            Order order = orderService.createOrder(
                user.getId(),
                orderItems,
                "123 Test St, Test City",
                "Credit Card"
            );
            
            return ResponseEntity.ok().body("Order created successfully with ID: " + order.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating order: " + e.getMessage());
        }
    }
}