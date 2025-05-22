package com.example.laptopstore.ui;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.model.Order;
import com.example.laptopstore.model.OrderItem;
import com.example.laptopstore.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

public class LaptopStoreUI extends JFrame {

    private static final String API_BASE_URL = "http://localhost:8084/api";
    private final RestTemplate restTemplate = new RestTemplate();
    
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel laptopsPanel;
    private JPanel cartPanel;
    private JPanel ordersPanel;
    private JPanel profilePanel;
    private JTextField searchField;
    private JComboBox<String> brandFilter;
    private JPanel laptopsGrid;
    private JLabel totalLabel;
    private JButton checkoutButton;
    
    private List<Laptop> laptops = new ArrayList<>();
    private List<Laptop> filteredLaptops = new ArrayList<>();
    private List<OrderItem> cart = new ArrayList<>();
    private User currentUser; // In a real app, this would be set after login
    
    public LaptopStoreUI() {
        // For demo purposes, initialize a dummy user
        currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername("user");
        
        setTitle("Laptop Store");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
        
        // Fetch laptops when the app starts
        fetchLaptops();
        
        setVisible(true);
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();
        
        // Initialize panels
        laptopsPanel = createLaptopsPanel();
        cartPanel = createCartPanel();
        ordersPanel = createOrdersPanel();
        profilePanel = createProfilePanel();
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Laptops", laptopsPanel);
        tabbedPane.addTab("Cart", cartPanel);
        tabbedPane.addTab("My Orders", ordersPanel);
        tabbedPane.addTab("Profile", profilePanel);
        
        // Add tab change listener to refresh data
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 0) { // Laptops tab
                fetchLaptops();
            } else if (selectedIndex == 2) { // Orders tab
                fetchOrders();
            }
        });
    }
    
    private void layoutComponents() {
        // Add tabbed pane to main panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel titleLabel = new JLabel("Laptop Store");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("Logged in as: " + currentUser.getUsername());
        userLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(500));
        headerPanel.add(userLabel);
        
        return headerPanel;
    }
    
    private JPanel createLaptopsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search: "));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        
        brandFilter = new JComboBox<>(new String[]{"All Brands", "Apple"});
        searchPanel.add(new JLabel("Brand: "));
        searchPanel.add(brandFilter);
        
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchLaptops());
        searchPanel.add(searchButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Laptops display
        laptopsGrid = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(laptopsGrid);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createLaptopCard(Laptop laptop) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Image placeholder
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(200, 150));
        imagePanel.setBackground(Color.LIGHT_GRAY);
        JLabel imageLabel = new JLabel("[" + laptop.getBrand() + " Image]");
        imagePanel.add(imageLabel);
        
        // Format the price
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = currencyFormatter.format(laptop.getPrice());
        
        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        JLabel nameLabel = new JLabel(laptop.getBrand() + " " + laptop.getModel());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(nameLabel);
        infoPanel.add(new JLabel(formattedPrice));
        infoPanel.add(new JLabel(laptop.getProcessor() + ", " + laptop.getRam()));
        infoPanel.add(new JLabel(laptop.getStorage()));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> showLaptopDetails(laptop));
        
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(new Color(46, 204, 113));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.addActionListener(e -> addToCart(laptop));
        
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(addToCartButton);
        
        card.add(imagePanel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void showLaptopDetails(Laptop laptop) {
        // Create a dialog to show detailed laptop information
        JDialog dialog = new JDialog(this, "Laptop Details", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header with laptop name
        JLabel titleLabel = new JLabel(laptop.getBrand() + " " + laptop.getModel());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        // Format the price
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = currencyFormatter.format(laptop.getPrice());
        
        // Add all the laptop details
        detailsPanel.add(new JLabel("Price: " + formattedPrice));
        detailsPanel.add(new JLabel("Brand: " + laptop.getBrand()));
        detailsPanel.add(new JLabel("Model: " + laptop.getModel()));
        detailsPanel.add(new JLabel("Processor: " + laptop.getProcessor()));
        detailsPanel.add(new JLabel("RAM: " + laptop.getRam()));
        detailsPanel.add(new JLabel("Storage: " + laptop.getStorage()));
        detailsPanel.add(new JLabel("Display Size: " + laptop.getDisplaySize()));
        detailsPanel.add(new JLabel("Resolution: " + laptop.getResolution()));
        detailsPanel.add(new JLabel("Graphics Card: " + laptop.getGraphicsCard()));
        detailsPanel.add(new JLabel("Operating System: " + laptop.getOperatingSystem()));
        detailsPanel.add(new JLabel("Weight: " + laptop.getWeight()));
        detailsPanel.add(new JLabel("Dimensions: " + laptop.getDimensions()));
        detailsPanel.add(new JLabel("Battery Life: " + laptop.getBatteryLife()));
        detailsPanel.add(new JLabel("Color: " + laptop.getColor()));
        
        if (laptop.getDescription() != null && !laptop.getDescription().isEmpty()) {
            JTextArea descriptionArea = new JTextArea(laptop.getDescription());
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setLineWrap(true);
            descriptionArea.setEditable(false);
            descriptionArea.setBorder(BorderFactory.createTitledBorder("Description"));
            JScrollPane scrollPane = new JScrollPane(descriptionArea);
            scrollPane.setPreferredSize(new Dimension(400, 100));
            detailsPanel.add(scrollPane);
        }
        
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> {
            addToCart(laptop);
            dialog.dispose();
        });
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        
        buttonsPanel.add(addToCartButton);
        buttonsPanel.add(closeButton);
        
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Cart items panel (will be populated dynamically)
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Total and checkout panel
        JPanel checkoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        checkoutButton = new JButton("Checkout");
        checkoutButton.setEnabled(false);
        checkoutButton.addActionListener(e -> checkout());
        
        checkoutPanel.add(totalLabel);
        checkoutPanel.add(checkoutButton);
        
        panel.add(checkoutPanel, BorderLayout.SOUTH);
        
        // Store the panel reference
        cartPanel = panel;
        
        // Update the cart view initially
        SwingUtilities.invokeLater(() -> updateCartView(cartItemsPanel));
        
        return panel;
    }
    
    private void updateCartView(JPanel cartItemsPanel) {
        cartItemsPanel.removeAll();
        
        if (cart.isEmpty()) {
            JLabel emptyCartLabel = new JLabel("Your cart is empty");
            emptyCartLabel.setHorizontalAlignment(JLabel.CENTER);
            emptyCartLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            emptyCartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartItemsPanel.add(emptyCartLabel);
            
            totalLabel.setText("Total: $0.00");
            checkoutButton.setEnabled(false);
        } else {
            BigDecimal total = BigDecimal.ZERO;
            
            for (OrderItem item : cart) {
                JPanel itemPanel = createCartItemPanel(item);
                cartItemsPanel.add(itemPanel);
                cartItemsPanel.add(Box.createVerticalStrut(10));
                
                total = total.add(item.getSubtotal());
            }
            
            // Format the total price
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedTotal = currencyFormatter.format(total);
            totalLabel.setText("Total: " + formattedTotal);
            checkoutButton.setEnabled(true);
        }
        
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }
    
    private JPanel createCartItemPanel(OrderItem item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        Laptop laptop = item.getLaptop();
        
        // Left side: laptop info
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(new JLabel(laptop.getBrand() + " " + laptop.getModel()));
        
        // Format the prices
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedUnitPrice = currencyFormatter.format(item.getUnitPrice());
        String formattedSubtotal = currencyFormatter.format(item.getSubtotal());
        
        infoPanel.add(new JLabel("Unit Price: " + formattedUnitPrice));
        infoPanel.add(new JLabel("Quantity: " + item.getQuantity()));
        
        // Right side: subtotal and remove button
        JPanel actionsPanel = new JPanel(new GridLayout(2, 1));
        JLabel subtotalLabel = new JLabel("Subtotal: " + formattedSubtotal);
        subtotalLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> removeFromCart(item));
        
        actionsPanel.add(subtotalLabel);
        actionsPanel.add(removeButton);
        
        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(actionsPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel ordersListPanel = new JPanel();
        ordersListPanel.setLayout(new BoxLayout(ordersListPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(ordersListPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add a refresh button
        JButton refreshButton = new JButton("Refresh Orders");
        refreshButton.addActionListener(e -> fetchOrders());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Store this panel reference before returning it
        ordersPanel = panel;
        
        // Now load orders initially after the panel is stored
        SwingUtilities.invokeLater(() -> fetchOrders());
        
        return panel;
    }
    
    private void updateOrdersView(List<Order> orders, JPanel ordersListPanel) {
        ordersListPanel.removeAll();
        
        if (orders == null || orders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("You don't have any orders yet");
            noOrdersLabel.setHorizontalAlignment(JLabel.CENTER);
            noOrdersLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noOrdersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ordersListPanel.add(noOrdersLabel);
        } else {
            // Add each order to the panel
            for (Order order : orders) {
                JPanel orderPanel = createOrderPanel(order);
                ordersListPanel.add(orderPanel);
                ordersListPanel.add(Box.createVerticalStrut(20));
            }
        }
        
        ordersListPanel.revalidate();
        ordersListPanel.repaint();
    }
    
    private JPanel createOrderPanel(Order order) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Order #" + order.getId()));
        
        // Order info panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2));
        infoPanel.add(new JLabel("Date:"));
        infoPanel.add(new JLabel(order.getOrderDate().toString()));
        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(new JLabel(order.getStatus().toString()));
        infoPanel.add(new JLabel("Total:"));
        
        // Format the total price
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedTotal = currencyFormatter.format(order.getTotalAmount());
        infoPanel.add(new JLabel(formattedTotal));
        
        panel.add(infoPanel, BorderLayout.NORTH);
        
        // Order items
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        
        for (OrderItem item : order.getOrderItems()) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            itemPanel.add(new JLabel(item.getLaptop().getBrand() + " " + item.getLaptop().getModel()));
            itemPanel.add(new JLabel("Qty: " + item.getQuantity()));
            itemPanel.add(new JLabel("Price: " + currencyFormatter.format(item.getUnitPrice())));
            itemsPanel.add(itemPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 15, 5);
        
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, gbc);
        
        // Reset gridwidth for the rest
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        String[] fields = {"Username", "First Name", "Last Name", "Email", "Address", "Phone Number"};
        JTextField[] textFields = new JTextField[fields.length];
        
        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(new JLabel(fields[i] + ":"), gbc);
            
            gbc.gridx = 1;
            textFields[i] = new JTextField(20);
            
            // Populate with current user data if available
            if (currentUser != null) {
                switch (i) {
                    case 0: textFields[i].setText(currentUser.getUsername()); break;
                    case 1: textFields[i].setText(currentUser.getFirstName()); break;
                    case 2: textFields[i].setText(currentUser.getLastName()); break;
                    case 3: textFields[i].setText(currentUser.getEmail()); break;
                    case 4: textFields[i].setText(currentUser.getAddress()); break;
                    case 5: textFields[i].setText(currentUser.getPhoneNumber()); break;
                }
            }
            
            panel.add(textFields[i], gbc);
        }
        
        gbc.gridx = 1;
        gbc.gridy = fields.length + 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveUserProfile(textFields));
        panel.add(saveButton, gbc);
        
        return panel;
    }
    
    // API and Business Logic Methods
    
    private void fetchLaptops() {
        try {
            // Set up headers to accept JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Make GET request to fetch all laptops
            ResponseEntity<List<Laptop>> response = restTemplate.exchange(
                API_BASE_URL + "/laptops",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Laptop>>(){});
            
            if (response.getStatusCode().is2xxSuccessful()) {
                laptops = response.getBody();
                // If no laptops are returned, use dummy data for testing
                if (laptops == null || laptops.isEmpty()) {
                    laptops = createDummyLaptops();
                }
                displayLaptops(laptops);
            } else {
                showError("Failed to fetch laptops: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            showError("Error from server: " + e.getStatusCode() + " - " + e.getStatusText());
            // Fall back to dummy data
            laptops = createDummyLaptops();
            displayLaptops(laptops);
        } catch (RestClientException e) {
            showError("Error connecting to the server: " + e.getMessage());
            // Fall back to dummy data
            laptops = createDummyLaptops();
            displayLaptops(laptops);
        }
    }
    
    private List<Laptop> createDummyLaptops() {
        List<Laptop> dummyLaptops = new ArrayList<>();
        
        Laptop macbookAir = new Laptop();
        macbookAir.setId(1L);
        macbookAir.setBrand("Apple");
        macbookAir.setModel("MacBook Air M4 (2025)");
        macbookAir.setDescription("Light and powerful laptop with Apple M4 chip, perfect for everyday use and lightweight tasks.");
        macbookAir.setPrice(new BigDecimal("1199.00"));
        macbookAir.setProcessor("Apple M4 (10-core CPU)");
        macbookAir.setRam("16GB unified memory");
        macbookAir.setStorage("512GB SSD");
        macbookAir.setDisplaySize("13.6-inch");
        macbookAir.setResolution("2560 x 1664 Liquid Retina display");
        macbookAir.setGraphicsCard("10-core GPU");
        macbookAir.setOperatingSystem("macOS");
        macbookAir.setWeight("1.24 kg");
        macbookAir.setDimensions("304.1 x 215 x 11.3 mm");
        macbookAir.setBatteryLife("Up to 18 hours");
        macbookAir.setColor("Silver");
        macbookAir.setImagePath("/images/macbook-air-m4.jpg");
        macbookAir.setStockQuantity(15);
        macbookAir.setIsAvailable(true);
        dummyLaptops.add(macbookAir);
        
        Laptop macbookPro = new Laptop();
        macbookPro.setId(2L);
        macbookPro.setBrand("Apple");
        macbookPro.setModel("MacBook Pro 14 M4 Pro (2025)");
        macbookPro.setDescription("Professional-grade laptop with M4 Pro chip, designed for demanding tasks like coding, video editing, and 3D rendering.");
        macbookPro.setPrice(new BigDecimal("1999.00"));
        macbookPro.setProcessor("Apple M4 Pro (12-core CPU)");
        macbookPro.setRam("32GB unified memory");
        macbookPro.setStorage("1TB SSD");
        macbookPro.setDisplaySize("14.2-inch");
        macbookPro.setResolution("3024 x 1964 Liquid Retina XDR display");
        macbookPro.setGraphicsCard("16-core GPU");
        macbookPro.setOperatingSystem("macOS");
        macbookPro.setWeight("1.55 kg");
        macbookPro.setDimensions("312.6 x 221.2 x 15.5 mm");
        macbookPro.setBatteryLife("Up to 20 hours");
        macbookPro.setColor("Space Gray");
        macbookPro.setImagePath("/images/macbook-pro-m4.jpg");
        macbookPro.setStockQuantity(10);
        macbookPro.setIsAvailable(true);
        dummyLaptops.add(macbookPro);
        
        Laptop macbookProMax = new Laptop();
        macbookProMax.setId(3L);
        macbookProMax.setBrand("Apple");
        macbookProMax.setModel("MacBook Pro 16 M4 Max (2025)");
        macbookProMax.setDescription("The most powerful MacBook ever with M4 Max chip, designed for professional video, audio and graphics work.");
        macbookProMax.setPrice(new BigDecimal("3499.00"));
        macbookProMax.setProcessor("Apple M4 Max (14-core CPU)");
        macbookProMax.setRam("64GB unified memory");
        macbookProMax.setStorage("2TB SSD");
        macbookProMax.setDisplaySize("16.2-inch");
        macbookProMax.setResolution("3456 x 2234 Liquid Retina XDR display");
        macbookProMax.setGraphicsCard("32-core GPU");
        macbookProMax.setOperatingSystem("macOS");
        macbookProMax.setWeight("2.16 kg");
        macbookProMax.setDimensions("355.7 x 248.1 x 16.8 mm");
        macbookProMax.setBatteryLife("Up to 24 hours");
        macbookProMax.setColor("Space Black");
        macbookProMax.setImagePath("/images/macbook-pro-max-m4.jpg");
        macbookProMax.setStockQuantity(5);
        macbookProMax.setIsAvailable(true);
        dummyLaptops.add(macbookProMax);
        
        return dummyLaptops;
    }
    
    private void searchLaptops() {
        String searchTerm = searchField.getText().trim();
        String brand = (String) brandFilter.getSelectedItem();
        
        try {
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Build the API URL with query parameters
            StringBuilder urlBuilder = new StringBuilder(API_BASE_URL + "/laptops/search?");
            
            // Add search term if not empty
            if (!searchTerm.isEmpty()) {
                urlBuilder.append("keyword=").append(searchTerm);
            }
            
            // Add brand filter if not "All Brands"
            if (!"All Brands".equals(brand)) {
                if (!searchTerm.isEmpty()) {
                    urlBuilder.append("&");
                }
                urlBuilder.append("brand=").append(brand);
            }
            
            // Make GET request with search parameters
            ResponseEntity<List<Laptop>> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Laptop>>(){});
            
            if (response.getStatusCode().is2xxSuccessful()) {
                filteredLaptops = response.getBody();
                if (filteredLaptops == null) {
                    filteredLaptops = new ArrayList<>();
                }
                displayLaptops(filteredLaptops);
            } else {
                showError("Failed to search laptops: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            showError("Error from server: " + e.getStatusCode() + " - " + e.getStatusText());
            // Fall back to local filtering
            localSearchLaptops(searchTerm, brand);
        } catch (RestClientException e) {
            showError("Error connecting to the server: " + e.getMessage());
            // Fall back to local filtering
            localSearchLaptops(searchTerm, brand);
        }
    }
    
    private void localSearchLaptops(String searchTerm, String brand) {
        filteredLaptops.clear();
        
        for (Laptop laptop : laptops) {
            boolean brandMatch = "All Brands".equals(brand) || laptop.getBrand().equals(brand);
            boolean searchMatch = searchTerm.isEmpty() ||
                    laptop.getBrand().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    laptop.getModel().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    (laptop.getDescription() != null && laptop.getDescription().toLowerCase().contains(searchTerm.toLowerCase()));
            
            if (brandMatch && searchMatch) {
                filteredLaptops.add(laptop);
            }
        }
        
        displayLaptops(filteredLaptops);
    }
    
    private void displayLaptops(List<Laptop> laptopsToDisplay) {
        laptopsGrid.removeAll();
        
        if (laptopsToDisplay.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No laptops found");
            noResultsLabel.setHorizontalAlignment(JLabel.CENTER);
            noResultsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            laptopsGrid.add(noResultsLabel);
        } else {
            for (Laptop laptop : laptopsToDisplay) {
                laptopsGrid.add(createLaptopCard(laptop));
            }
        }
        
        laptopsGrid.revalidate();
        laptopsGrid.repaint();
    }
    
    private void addToCart(Laptop laptop) {
        // Check if the laptop is already in cart
        boolean found = false;
        for (OrderItem item : cart) {
            if (item.getLaptop().getId().equals(laptop.getId())) {
                // Increase quantity
                item.setQuantity(item.getQuantity() + 1);
                item.setSubtotal(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
                found = true;
                break;
            }
        }
        
        if (!found) {
            // Add new item to cart
            OrderItem newItem = new OrderItem();
            newItem.setLaptop(laptop);
            newItem.setQuantity(1);
            newItem.setUnitPrice(laptop.getPrice());
            newItem.setSubtotal(laptop.getPrice());
            cart.add(newItem);
        }
        
        // Update the cart view
        JPanel cartItemsPanel = (JPanel) ((JScrollPane) cartPanel.getComponent(0)).getViewport().getView();
        updateCartView(cartItemsPanel);
        
        // Show confirmation
        JOptionPane.showMessageDialog(this, "Added " + laptop.getBrand() + " " + laptop.getModel() + " to cart.", "Added to Cart", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void removeFromCart(OrderItem itemToRemove) {
        cart.remove(itemToRemove);
        
        // Update the cart view
        JPanel cartItemsPanel = (JPanel) ((JScrollPane) cartPanel.getComponent(0)).getViewport().getView();
        updateCartView(cartItemsPanel);
    }
    
    private void checkout() {
        if (cart.isEmpty()) {
            showError("Your cart is empty.");
            return;
        }
        
        // In a real app, we would send an API request to create the order
        // For simplicity, we'll show a confirmation dialog and clear the cart
        
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Proceed with checkout?",
                "Confirm Order",
                JOptionPane.YES_NO_OPTION);
        
        if (confirmed == JOptionPane.YES_OPTION) {
            // Create the order via API
            createOrder();
        }
    }
    
    private void createOrder() {
        try {
            // Create the order with items from cart
            Order newOrder = new Order();
            newOrder.setUser(currentUser);
            newOrder.setOrderItems(new ArrayList<>(cart));
            newOrder.setOrderDate(java.time.LocalDateTime.now());
            newOrder.setStatus(Order.OrderStatus.PENDING);
            
            // Calculate total amount
            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem item : cart) {
                total = total.add(item.getSubtotal());
            }
            newOrder.setTotalAmount(total);
            
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Order> requestEntity = new HttpEntity<>(newOrder, headers);
            
            // Send POST request to create order
            ResponseEntity<Order> response = restTemplate.postForEntity(
                API_BASE_URL + "/orders", 
                requestEntity, 
                Order.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // Clear the cart
                cart.clear();
                
                // Update the cart view
                JPanel cartItemsPanel = (JPanel) ((JScrollPane) cartPanel.getComponent(0)).getViewport().getView();
                updateCartView(cartItemsPanel);
                
                // Show success message
                JOptionPane.showMessageDialog(this,
                        "Your order has been placed successfully!",
                        "Order Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Switch to the Orders tab
                tabbedPane.setSelectedIndex(2);
                fetchOrders();
            } else {
                showError("Failed to create order: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            showError("Error from server: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (RestClientException e) {
            showError("Failed to create order: " + e.getMessage());
        }
    }
    
    private void fetchOrders() {
        try {
            // Set up headers to accept JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Make GET request to fetch orders for the current user
            ResponseEntity<List<Order>> response = restTemplate.exchange(
                API_BASE_URL + "/orders/user/" + currentUser.getId(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Order>>(){});
            
            if (response.getStatusCode().is2xxSuccessful()) {
                List<Order> orders = response.getBody();
                // If no orders are returned, use dummy data for testing
                if (orders == null || orders.isEmpty()) {
                    orders = createDummyOrders();
                }
                
                // Update the orders view
                JPanel ordersListPanel = (JPanel) ((JScrollPane) ordersPanel.getComponent(0)).getViewport().getView();
                updateOrdersView(orders, ordersListPanel);
            } else {
                showError("Failed to fetch orders: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            showError("Error from server: " + e.getStatusCode() + " - " + e.getStatusText());
            // Fall back to dummy data
            List<Order> orders = createDummyOrders();
            JPanel ordersListPanel = (JPanel) ((JScrollPane) ordersPanel.getComponent(0)).getViewport().getView();
            updateOrdersView(orders, ordersListPanel);
        } catch (RestClientException e) {
            showError("Error connecting to the server: " + e.getMessage());
            // Fall back to dummy data
            List<Order> orders = createDummyOrders();
            JPanel ordersListPanel = (JPanel) ((JScrollPane) ordersPanel.getComponent(0)).getViewport().getView();
            updateOrdersView(orders, ordersListPanel);
        }
    }
    
    private List<Order> createDummyOrders() {
        List<Order> dummyOrders = new ArrayList<>();
        
        // Create a dummy order with the first laptop
        if (!laptops.isEmpty()) {
            Order order = new Order();
            order.setId(1L);
            order.setOrderDate(java.time.LocalDateTime.now().minusDays(2));
            order.setStatus(Order.OrderStatus.DELIVERED);
            order.setTotalAmount(new BigDecimal("1199.00"));
            
            // Create order item
            OrderItem item = new OrderItem();
            item.setLaptop(laptops.get(0));
            item.setQuantity(1);
            item.setUnitPrice(laptops.get(0).getPrice());
            item.setSubtotal(laptops.get(0).getPrice());
            
            List<OrderItem> items = new ArrayList<>();
            items.add(item);
            order.setOrderItems(items);
            
            dummyOrders.add(order);
        }
        
        return dummyOrders;
    }
    
    private void saveUserProfile(JTextField[] fields) {
        try {
            // Update the current user object
            currentUser.setUsername(fields[0].getText());
            currentUser.setFirstName(fields[1].getText());
            currentUser.setLastName(fields[2].getText());
            currentUser.setEmail(fields[3].getText());
            currentUser.setAddress(fields[4].getText());
            currentUser.setPhoneNumber(fields[5].getText());
            
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> requestEntity = new HttpEntity<>(currentUser, headers);
            
            // Send PUT request to update user
            ResponseEntity<User> response = restTemplate.exchange(
                API_BASE_URL + "/users/" + currentUser.getId(),
                HttpMethod.PUT,
                requestEntity,
                User.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // Update the current user with the response
                currentUser = response.getBody();
                
                JOptionPane.showMessageDialog(this,
                        "Profile updated successfully!",
                        "Profile Update",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                showError("Failed to update profile: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            showError("Error from server: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (RestClientException e) {
            showError("Failed to update profile: " + e.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LaptopStoreUI());
    }
}