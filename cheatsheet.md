# Laptop Store Exercises Cheat Sheet

This cheat sheet provides guidance on solving each exercise in the Laptop Store application. Since you're using a RAG-based AI coding assistant, the prompts include specific file references to help the AI provide context-aware solutions.

## Exercise 1: Understand the Codebase

### Effective Prompts

1. **"Explain the architecture of src/main/java/com/example/laptopstore/controller/WebController.java and how it interacts with other components."**
   - *Why it works*: Grounds the explanation in a central file while exploring its relationships.

2. **"Analyze src/main/java/com/example/laptopstore/controller/LaptopController.java and explain its endpoints and functionality."**
   - *Why it works*: Directs focus to a specific controller file to understand API patterns.

3. **"Show me how an order flows through the system by examining src/main/java/com/example/laptopstore/service/OrderService.java and related files."**
   - *Why it works*: Starts with a key service file and expands to understand dependencies.

4. **"Explain how the cart works by analyzing src/main/resources/templates/cart.html and src/main/java/com/example/laptopstore/controller/WebController.java."**
   - *Why it works*: References both frontend and backend components of the cart system.

## Exercise 2: Implement Cart and Wishlist for Mice

### Effective Prompts

1. **"Compare src/main/resources/templates/laptops/details.html with src/main/resources/templates/mouse/details.html and help me implement cart functionality for mice."**
   - *Why it works*: Directly compares the existing implementation with the file that needs changes.

2. **"Based on src/main/java/com/example/laptopstore/controller/WebController.java (lines 186-217), how should I modify the CartItemRequest class to handle mice products?"**
   - *Why it works*: Pinpoints the exact section of code that needs to be updated.

3. **"Using src/main/resources/templates/laptops/details.html (lines 103-157) as reference, write JavaScript code for src/main/resources/templates/mouse/details.html to add mice to the cart."**
   - *Why it works*: Provides the exact source of the reference implementation and target file.

4. **"Help me update src/main/resources/templates/cart.html to handle both laptop and mouse product types."**
   - *Why it works*: Focuses on the specific template file that needs modification.

### Implementation Steps
1. Add product type field to cart data structure in mouse/details.html
2. Enable cart buttons in mouse/details.html
3. Implement JavaScript for cart functionality based on laptops/details.html
4. Update cart.html to detect and display product type
5. Modify WebController.java to handle mouse products in CartItemRequest

## Exercise 3: Debug a Complex Rendering Error

### Effective Prompts

1. **"I'm getting a ClassCastException on the orders page. Analyze src/main/java/com/example/laptopstore/controller/WebController.java (method orderHistory) and src/main/resources/templates/orders/history.html to find the issue."**
   - *Why it works*: Specifies the error type and the exact files involved in the bug.

2. **"Debug the processOrders() method in src/main/java/com/example/laptopstore/controller/WebController.java and explain why it causes a ClassCastException in the template."**
   - *Why it works*: Pinpoints the problematic method and connects it to the template error.

3. **"Fix the type mismatch between what WebController.java's processOrders() method returns and what orders/history.html expects."**
   - *Why it works*: Directly addresses the core issue - the type mismatch.

### Debug Steps
1. Examine WebController.java's orderHistory() and processOrders() methods
2. Notice that processOrders() sometimes returns a String instead of List<Order>
3. Check orders/history.html to see it expects a List<Order> to iterate over
4. Modify processOrders() to always return a List<Order> type
5. Test the fix by creating an order and viewing the orders page

## Exercise 4: Refactor DataLoader for Better Code Quality

### Effective Prompts

1. **"Refactor src/main/java/com/example/laptopstore/config/DataLoader.java using the Builder pattern to reduce code duplication."**
   - *Why it works*: Specifies the file and the design pattern to implement.

2. **"Create LaptopBuilder and MouseBuilder classes in src/main/java/com/example/laptopstore/config/DataLoader.java to improve code quality."**
   - *Why it works*: Details exactly which helper classes to create and where.

3. **"Optimize src/main/java/com/example/laptopstore/config/DataLoader.java by implementing batch processing with saveAll() instead of individual save() calls."**
   - *Why it works*: Focuses on a specific optimization technique in the target file.

4. **"Analyze src/main/java/com/example/laptopstore/model/Laptop.java and src/main/java/com/example/laptopstore/model/Mouse.java to create appropriate builder methods for DataLoader.java."**
   - *Why it works*: References the model classes to ensure proper builder implementation.

### Refactoring Approach
1. Create inner builder classes for Laptop and Mouse in DataLoader.java
2. Group related properties in semantic builder methods (display, dimensions, etc.)
3. Use Arrays.asList() to create collections of objects
4. Replace individual save() calls with saveAll()
5. Set default values in builders (imagePath, isAvailable)

## Exercise 5: Add a New Accessory Category

### Effective Prompts

1. **"Based on src/main/java/com/example/laptopstore/model/Mouse.java, help me create a Keyboard.java model with appropriate fields."**
   - *Why it works*: Uses the existing model as a template for the new one.

2. **"Using src/main/java/com/example/laptopstore/repository/MouseRepository.java as reference, create a KeyboardRepository interface."**
   - *Why it works*: Focuses on creating a specific component with a clear reference.

3. **"Compare src/main/java/com/example/laptopstore/service/MouseService.java and src/main/java/com/example/laptopstore/controller/MouseController.java to help me implement similar classes for keyboards."**
   - *Why it works*: References both service and controller implementations for the pattern.

4. **"Using src/main/java/com/example/laptopstore/controller/WebMouseController.java and src/main/resources/templates/mouse/list.html as templates, create web controller and views for keyboards."**
   - *Why it works*: Covers both controller and view components for the web interface.

5. **"Based on the keyboard data in src/main/java/com/example/laptopstore/config/DataLoader.java (loadMice method), create a loadKeyboards method with sample data."**
   - *Why it works*: Uses the existing data loading pattern for the new product type.

6. **"Update src/main/resources/templates/layout.html to add keyboards to the navigation menu, similar to how mice are included."**
   - *Why it works*: Specifies exactly where and how to update the navigation.

### Key Files to Create/Modify
- `src/main/java/com/example/laptopstore/model/Keyboard.java`
- `src/main/java/com/example/laptopstore/repository/KeyboardRepository.java`
- `src/main/java/com/example/laptopstore/service/KeyboardService.java`
- `src/main/java/com/example/laptopstore/controller/KeyboardController.java`
- `src/main/java/com/example/laptopstore/controller/WebKeyboardController.java`
- `src/main/resources/templates/keyboard/list.html` and `details.html`
- `src/main/java/com/example/laptopstore/config/DataLoader.java`
- `src/main/resources/templates/layout.html`

## Exercise 6: Add Unit and Integration Tests

### Effective Prompts

1. **"Based on src/main/java/com/example/laptopstore/service/OrderService.java, create JUnit tests for the createOrder and cancelOrder methods."**
   - *Why it works*: Focuses testing on specific, complex methods in the service layer.

2. **"Using src/main/java/com/example/laptopstore/repository/LaptopRepository.java, create integration tests with @DataJpaTest annotation."**
   - *Why it works*: Specifies both the component to test and the testing approach.

3. **"Based on src/main/java/com/example/laptopstore/controller/LaptopController.java, write MockMvc tests for the getAllLaptops and getLaptopById endpoints."**
   - *Why it works*: Identifies specific controller endpoints to test with the right testing framework.

4. **"Create test fixtures for src/test/java/com/example/laptopstore/controller/WebControllerTest.java to test the order history functionality."**
   - *Why it works*: Focuses on creating reusable test data for a specific test class.

### Testing Approach
1. For service tests: Use Mockito to mock repositories and other dependencies
2. For repository tests: Use @DataJpaTest and H2 in-memory database
3. For controller tests: Use MockMvc and mock the service layer
4. Test both happy path and error scenarios
5. Create test fixtures in separate classes for reusability