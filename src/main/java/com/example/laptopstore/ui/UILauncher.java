package com.example.laptopstore.ui;

import javax.swing.SwingUtilities;

public class UILauncher {
    
    public static void main(String[] args) {
        // Launch the Swing UI
        SwingUtilities.invokeLater(() -> {
            new LaptopStoreUI();
        });
    }
}