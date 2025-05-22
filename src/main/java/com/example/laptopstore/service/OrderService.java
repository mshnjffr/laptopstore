package com.example.laptopstore.service;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.model.Order;
import com.example.laptopstore.model.OrderItem;
import com.example.laptopstore.model.User;
import com.example.laptopstore.repository.OrderItemRepository;
import com.example.laptopstore.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final LaptopService laptopService;
    private final UserService userService;
    
    @Autowired
    public OrderService(OrderRepository orderRepository, 
                        OrderItemRepository orderItemRepository,
                        LaptopService laptopService,
                        UserService userService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.laptopService = laptopService;
        this.userService = userService;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }
    
    public List<Order> getOrdersByUser(Long userId) {
        User user = userService.getUserById(userId);
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
    
    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items, String shippingAddress, String paymentMethod) {
        User user = userService.getUserById(userId);
        
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress != null ? shippingAddress : "Default shipping address");
        order.setPaymentMethod(paymentMethod != null ? paymentMethod : "Default payment method");
        
        // Calculate total amount and save the order
        BigDecimal totalAmount = BigDecimal.ZERO;
        order = orderRepository.save(order);
        
        for (OrderItem item : items) {
            Laptop laptop = laptopService.getLaptopById(item.getLaptop().getId());
            
            // Check if laptop is available and has enough stock
            if (!laptop.getIsAvailable() || laptop.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Laptop " + laptop.getBrand() + " " + laptop.getModel() + " is not available in requested quantity");
            }
            
            // Update laptop stock
            laptopService.updateStock(laptop.getId(), laptop.getStockQuantity() - item.getQuantity());
            
            // Set order item details
            item.setOrder(order);
            item.setLaptop(laptop);
            item.setUnitPrice(laptop.getPrice());
            item.setSubtotal(laptop.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemRepository.save(item);
            
            totalAmount = totalAmount.add(item.getSubtotal());
        }
        
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }
    
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        
        // Only cancel if order is still in PENDING status
        if (order.getStatus() == Order.OrderStatus.PENDING) {
            order.setStatus(Order.OrderStatus.CANCELLED);
            orderRepository.save(order);
            
            // Return items to stock
            List<OrderItem> items = orderItemRepository.findByOrder(order);
            for (OrderItem item : items) {
                Laptop laptop = item.getLaptop();
                laptopService.updateStock(laptop.getId(), laptop.getStockQuantity() + item.getQuantity());
            }
        } else {
            throw new RuntimeException("Cannot cancel order with status: " + order.getStatus());
        }
    }
}