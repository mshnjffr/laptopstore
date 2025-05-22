package com.example.laptopstore.config;

import com.example.laptopstore.ui.LaptopStoreUI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.HeadlessException;
import javax.swing.SwingUtilities;

// Disabled for web-based frontend
//@Component
public class SwingUIRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SwingUIRunner.class);

    @Override
    public void run(String... args) {
        try {
            // Force non-headless mode
            System.setProperty("java.awt.headless", "false");
            
            // Start the Swing UI in a separate thread to not block Spring Boot
            logger.info("Launching Swing UI...");
            SwingUtilities.invokeLater(() -> {
                try {
                    new LaptopStoreUI();
                } catch (HeadlessException e) {
                    logger.error("Failed to create UI: {}", e.getMessage());
                    logger.error("This may be due to running in a headless environment without display support.");
                    logger.info("Access the REST API at http://localhost:8084/api");
                }
            });
        } catch (Exception e) {
            logger.error("Error launching UI: {}", e.getMessage());
            logger.info("Running in headless environment. Swing UI will not be launched.");
            logger.info("Access the REST API at http://localhost:8084/api");
        }
    }
}