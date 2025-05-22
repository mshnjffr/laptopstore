package com.example.laptopstore.controller;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.model.Order;
import com.example.laptopstore.model.OrderItem;
import com.example.laptopstore.model.User;
import com.example.laptopstore.service.LaptopService;
import com.example.laptopstore.service.OrderService;
import com.example.laptopstore.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private LaptopService laptopService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // For demo purposes, assume a logged-in user (id=1)
    private static final Long DEMO_USER_ID = 1L;

    @GetMapping("/")
    public String home(Model model) {
        List<Laptop> laptops = laptopService.getAllLaptops();
        model.addAttribute("laptops", laptops);
        return "index";
    }

    @GetMapping("/laptops")
    public String laptopList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String brand,
            Model model) {
        
        List<Laptop> laptops;
        if (keyword != null || brand != null) {
            laptops = laptopService.searchLaptops(keyword, brand);
        } else {
            laptops = laptopService.getAllLaptops();
        }
        
        model.addAttribute("laptops", laptops);
        model.addAttribute("keyword", keyword);
        model.addAttribute("brand", brand);
        return "laptops/list";
    }

    @GetMapping("/laptops/{id}")
    public String laptopDetails(@PathVariable Long id, Model model) {
        Laptop laptop = laptopService.getLaptopById(id);
        if (laptop == null) {
            return "redirect:/laptops";
        }
        model.addAttribute("laptop", laptop);
        return "laptops/details";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        // Get demo user for the checkout form
        User user = userService.getUserById(DEMO_USER_ID);
        model.addAttribute("user", user);
        return "cart";
    }

    @GetMapping("/orders")
    public String orderHistory(Model model) {
        // Get demo user
        User user = userService.getUserById(DEMO_USER_ID);
        String[] nameParts = null;
        String customerName = nameParts[0]; 
        model.addAttribute("customerName", customerName);
        
        if (user != null) {
            List<Order> orders = orderService.getOrdersByUser(user.getId());
            model.addAttribute("orders", orders);
        }
        return "orders/history";
    }

    @GetMapping("/orders/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        return "orders/details";
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        User user = userService.getUserById(DEMO_USER_ID);
        model.addAttribute("user", user);
        return "profile";
    }
    
    @GetMapping("/exercises")
    public String exercises() {
        return "exercises";
    }
    
    @PostMapping("/api/orders/create-from-cart")
    @ResponseBody
    public ResponseEntity<?> createOrderFromCart(
            @RequestBody CreateOrderRequest createOrderRequest) {
        
        System.out.println("Received order request: " + createOrderRequest);
        
        try {
            // Validate request
            if (createOrderRequest == null || createOrderRequest.getItems() == null || createOrderRequest.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Order must contain at least one item");
            }
            
            // Get the demo user
            User user = userService.getUserById(DEMO_USER_ID);
            
            // Create order items from cart items
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItemRequest item : createOrderRequest.getItems()) {
                if (item == null || item.getLaptopId() == null || item.getQuantity() == null) {
                    return ResponseEntity.badRequest().body("Invalid item in cart: " + item);
                }
                
                OrderItem orderItem = new OrderItem();
                
                // Get the laptop from the database
                Laptop laptop = laptopService.getLaptopById(item.getLaptopId());
                if (laptop == null) {
                    return ResponseEntity.badRequest().body("Laptop not found with id: " + item.getLaptopId());
                }
                
                orderItem.setLaptop(laptop);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setUnitPrice(laptop.getPrice());
                orderItem.setSubtotal(laptop.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                
                orderItems.add(orderItem);
            }
            
            // Create the order with default values for shipping and payment
            Order order = orderService.createOrder(
                user.getId(),
                orderItems,
                null,  // Default shipping address will be used
                null   // Default payment method will be used
            );
            
            // Return simple success message with plain text content type
            return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("Order created successfully with ID: " + order.getId());
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace
            String errorMessage = "Error creating order: " + e.getMessage();
            System.err.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.TEXT_PLAIN)
                .body(errorMessage);
        }
    }
    
    // Classes needed for the order creation request
    public static class CreateOrderRequest {
        private List<CartItemRequest> items;
        
        public List<CartItemRequest> getItems() { return items; }
        public void setItems(List<CartItemRequest> items) { this.items = items; }
        
        @Override
        public String toString() {
            return "CreateOrderRequest{" +
                "items=" + (items != null ? items.size() : "null") +
                "}";
        }
    }
    
    public static class CartItemRequest {
        private Long laptopId;
        private Integer quantity;
        
        public Long getLaptopId() { return laptopId; }
        public void setLaptopId(Long laptopId) { this.laptopId = laptopId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        @Override
        public String toString() {
            return "CartItemRequest{" +
                "laptopId=" + laptopId +
                ", quantity=" + quantity +
                "}";
        }
    }
}