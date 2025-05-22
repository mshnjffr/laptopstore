package com.example.laptopstore.config;

import com.example.laptopstore.model.Laptop;
import com.example.laptopstore.model.Mouse;
import com.example.laptopstore.model.User;
import com.example.laptopstore.repository.LaptopRepository;
import com.example.laptopstore.repository.MouseRepository;
import com.example.laptopstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;
    private final MouseRepository mouseRepository;

    @Autowired
    public DataLoader(LaptopRepository laptopRepository, UserRepository userRepository, MouseRepository mouseRepository) {
        this.laptopRepository = laptopRepository;
        this.userRepository = userRepository;
        this.mouseRepository = mouseRepository;
    }

    @Override
    public void run(String... args) {
        // Load sample users
        loadUsers();
        
        // Load sample laptops
        loadLaptops();
        
        // Load sample mice
        loadMice();
    }
    
    private void loadUsers() {
        if (userRepository.count() == 0) {
            // Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // In a real app, this would be hashed
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            
            // Create regular user
            User user = new User();
            user.setUsername("user");
            user.setPassword("user123"); // In a real app, this would be hashed
            user.setEmail("user@example.com");
            user.setFirstName("Regular");
            user.setLastName("User");
            user.setAddress("123 Main St, City, Country");
            user.setPhoneNumber("555-123-4567");
            userRepository.save(user);
        }
    }
    
    private void loadLaptops() {
        if (laptopRepository.count() == 0) {
            // MacBook Air M4 (2025)
            Laptop macbookAir = new Laptop();
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
            macbookAir.setImagePath("/images/laptop.jpg");
            macbookAir.setStockQuantity(15);
            laptopRepository.save(macbookAir);
            
            // MacBook Pro M4 Pro (2025)
            Laptop macbookPro = new Laptop();
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
            macbookPro.setImagePath("/images/laptop.jpg");
            macbookPro.setStockQuantity(10);
            laptopRepository.save(macbookPro);
            
            // MacBook Pro M4 Max (2025)
            Laptop macbookProMax = new Laptop();
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
            macbookProMax.setImagePath("/images/laptop.jpg");
            macbookProMax.setStockQuantity(5);
            laptopRepository.save(macbookProMax);
            
            // Dell XPS 13 Plus (2025)
            Laptop dellXps13 = new Laptop();
            dellXps13.setBrand("Dell");
            dellXps13.setModel("XPS 13 Plus (2025)");
            dellXps13.setDescription("Ultra-slim design with edge-to-edge keyboard and invisible touchpad, featuring Intel's latest CPU for exceptional performance.");
            dellXps13.setPrice(new BigDecimal("1499.00"));
            dellXps13.setProcessor("Intel Core Ultra 9 185H");
            dellXps13.setRam("32GB LPDDR5x");
            dellXps13.setStorage("1TB PCIe NVMe SSD");
            dellXps13.setDisplaySize("13.4-inch");
            dellXps13.setResolution("3840 x 2400 OLED Touch");
            dellXps13.setGraphicsCard("Intel Arc Graphics");
            dellXps13.setOperatingSystem("Windows 11 Pro");
            dellXps13.setWeight("1.26 kg");
            dellXps13.setDimensions("295.3 x 199.04 x 15.28 mm");
            dellXps13.setBatteryLife("Up to 12 hours");
            dellXps13.setColor("Platinum");
            dellXps13.setImagePath("/images/laptop.jpg");
            dellXps13.setStockQuantity(12);
            laptopRepository.save(dellXps13);
            
            // Dell XPS 15 (2025)
            Laptop dellXps15 = new Laptop();
            dellXps15.setBrand("Dell");
            dellXps15.setModel("XPS 15 (2025)");
            dellXps15.setDescription("Premium 15-inch laptop with stunning display and powerful performance for creative professionals.");
            dellXps15.setPrice(new BigDecimal("2199.00"));
            dellXps15.setProcessor("Intel Core Ultra 9 185H");
            dellXps15.setRam("32GB DDR5");
            dellXps15.setStorage("1TB PCIe NVMe SSD");
            dellXps15.setDisplaySize("15.6-inch");
            dellXps15.setResolution("3840 x 2400 OLED Touch");
            dellXps15.setGraphicsCard("NVIDIA GeForce RTX 4060");
            dellXps15.setOperatingSystem("Windows 11 Pro");
            dellXps15.setWeight("1.96 kg");
            dellXps15.setDimensions("344.72 x 230.14 x 18.54 mm");
            dellXps15.setBatteryLife("Up to 12 hours");
            dellXps15.setColor("Platinum");
            dellXps15.setImagePath("/images/laptop.jpg");
            dellXps15.setStockQuantity(8);
            laptopRepository.save(dellXps15);
            
            // HP Spectre x360 14 (2025)
            Laptop hpSpectre = new Laptop();
            hpSpectre.setBrand("HP");
            hpSpectre.setModel("Spectre x360 14 (2025)");
            hpSpectre.setDescription("Elegant 2-in-1 convertible laptop with stunning OLED display and powerful AI features for productivity and creativity.");
            hpSpectre.setPrice(new BigDecimal("1799.00"));
            hpSpectre.setProcessor("Intel Core Ultra 7 155H");
            hpSpectre.setRam("32GB LPDDR5x");
            hpSpectre.setStorage("1TB PCIe NVMe SSD");
            hpSpectre.setDisplaySize("14-inch");
            hpSpectre.setResolution("2.8K (2880 x 1800) OLED Touch");
            hpSpectre.setGraphicsCard("Intel Arc Graphics");
            hpSpectre.setOperatingSystem("Windows 11 Home");
            hpSpectre.setWeight("1.4 kg");
            hpSpectre.setDimensions("313.76 x 220.45 x 16.1 mm");
            hpSpectre.setBatteryLife("Up to 16 hours");
            hpSpectre.setColor("Nightfall Black");
            hpSpectre.setImagePath("/images/laptop.jpg");
            hpSpectre.setStockQuantity(10);
            laptopRepository.save(hpSpectre);
            
            // Lenovo ThinkPad X1 Carbon Gen 12 (2025)
            Laptop lenovoX1 = new Laptop();
            lenovoX1.setBrand("Lenovo");
            lenovoX1.setModel("ThinkPad X1 Carbon Gen 12 (2025)");
            lenovoX1.setDescription("Ultra-lightweight business laptop with enterprise-grade security features, military-grade durability, and all-day battery life.");
            lenovoX1.setPrice(new BigDecimal("1899.00"));
            lenovoX1.setProcessor("Intel Core Ultra 7 155H");
            lenovoX1.setRam("32GB LPDDR5x");
            lenovoX1.setStorage("1TB PCIe Gen4 SSD");
            lenovoX1.setDisplaySize("14-inch");
            lenovoX1.setResolution("2.8K (2880 x 1800) OLED");
            lenovoX1.setGraphicsCard("Intel Arc Graphics");
            lenovoX1.setOperatingSystem("Windows 11 Pro");
            lenovoX1.setWeight("1.12 kg");
            lenovoX1.setDimensions("315.6 x 222.5 x 15.36 mm");
            lenovoX1.setBatteryLife("Up to 19 hours");
            lenovoX1.setColor("Deep Black");
            lenovoX1.setImagePath("/images/laptop.jpg");
            lenovoX1.setStockQuantity(12);
            laptopRepository.save(lenovoX1);
            
            // ASUS ZenBook S 13 (2025)
            Laptop asusZenbook = new Laptop();
            asusZenbook.setBrand("ASUS");
            asusZenbook.setModel("ZenBook S 13 (2025)");
            asusZenbook.setDescription("Ultra-slim and lightweight laptop with ceramic aluminum body, stunning OLED display, and powerful performance.");
            asusZenbook.setPrice(new BigDecimal("1599.00"));
            asusZenbook.setProcessor("Intel Core Ultra 7 155H");
            asusZenbook.setRam("16GB LPDDR5x");
            asusZenbook.setStorage("1TB PCIe NVMe SSD");
            asusZenbook.setDisplaySize("13.3-inch");
            asusZenbook.setResolution("2.8K (2880 x 1800) OLED");
            asusZenbook.setGraphicsCard("Intel Arc Graphics");
            asusZenbook.setOperatingSystem("Windows 11 Home");
            asusZenbook.setWeight("1.0 kg");
            asusZenbook.setDimensions("296.2 x 216.3 x 10.9 mm");
            asusZenbook.setBatteryLife("Up to 15 hours");
            asusZenbook.setColor("Basalt Gray");
            asusZenbook.setImagePath("/images/laptop.jpg");
            asusZenbook.setStockQuantity(15);
            laptopRepository.save(asusZenbook);
            
            // ASUS ROG Zephyrus G14 (2025)
            Laptop asusRog = new Laptop();
            asusRog.setBrand("ASUS");
            asusRog.setModel("ROG Zephyrus G14 (2025)");
            asusRog.setDescription("Powerful gaming laptop in a compact 14-inch form factor, featuring the latest AMD processor and NVIDIA graphics.");
            asusRog.setPrice(new BigDecimal("2099.00"));
            asusRog.setProcessor("AMD Ryzen 9 8945HS");
            asusRog.setRam("32GB DDR5");
            asusRog.setStorage("1TB PCIe NVMe SSD");
            asusRog.setDisplaySize("14-inch");
            asusRog.setResolution("2560 x 1600 QHD+ 240Hz");
            asusRog.setGraphicsCard("NVIDIA GeForce RTX 4070");
            asusRog.setOperatingSystem("Windows 11 Home");
            asusRog.setWeight("1.6 kg");
            asusRog.setDimensions("312 x 227 x 18.5 mm");
            asusRog.setBatteryLife("Up to 10 hours");
            asusRog.setColor("Eclipse Gray");
            asusRog.setImagePath("/images/laptop.jpg");
            asusRog.setStockQuantity(8);
            laptopRepository.save(asusRog);
        }
    }
    
    private void loadMice() {
        if (mouseRepository.count() == 0) {
            // Logitech MX Master 3S (2025)
            Mouse logitechMXMaster3S = new Mouse();
            logitechMXMaster3S.setBrand("Logitech");
            logitechMXMaster3S.setModel("MX Master 3S (2025)");
            logitechMXMaster3S.setDescription("Premium wireless productivity mouse with MagSpeed electromagnetic scrolling and high-precision 8K sensor.");
            logitechMXMaster3S.setPrice(new BigDecimal("99.99"));
            logitechMXMaster3S.setConnectionType("Wireless/Bluetooth");
            logitechMXMaster3S.setDpi(8000);
            logitechMXMaster3S.setSensor("Darkfield High Precision");
            logitechMXMaster3S.setPollingRate(125);
            logitechMXMaster3S.setButtons(7);
            logitechMXMaster3S.setRgbLighting(false);
            logitechMXMaster3S.setBatteryLife("Up to 70 days");
            logitechMXMaster3S.setWeight("141 g");
            logitechMXMaster3S.setDimensions("124.9 x 84.3 x 51 mm");
            logitechMXMaster3S.setColor("Graphite");
            logitechMXMaster3S.setErgonomic(true);
            logitechMXMaster3S.setGripType("Palm");
            logitechMXMaster3S.setImagePath("/images/mouse.jpg");
            logitechMXMaster3S.setStockQuantity(20);
            logitechMXMaster3S.setIsAvailable(true);
            mouseRepository.save(logitechMXMaster3S);
            
            // Logitech G Pro X Superlight 2 (2025)
            Mouse logitechGProX2 = new Mouse();
            logitechGProX2.setBrand("Logitech");
            logitechGProX2.setModel("G Pro X Superlight 2 (2025)");
            logitechGProX2.setDescription("Ultra-lightweight gaming mouse designed for esports professionals with HERO 2 sensor.");
            logitechGProX2.setPrice(new BigDecimal("159.99"));
            logitechGProX2.setConnectionType("Wireless");
            logitechGProX2.setDpi(32000);
            logitechGProX2.setSensor("HERO 2 Sensor");
            logitechGProX2.setPollingRate(4000);
            logitechGProX2.setButtons(5);
            logitechGProX2.setRgbLighting(false);
            logitechGProX2.setBatteryLife("Up to 95 hours");
            logitechGProX2.setWeight("60 g");
            logitechGProX2.setDimensions("125.0 x 63.5 x 40.0 mm");
            logitechGProX2.setColor("White");
            logitechGProX2.setErgonomic(false);
            logitechGProX2.setGripType("Claw/Fingertip");
            logitechGProX2.setImagePath("/images/mouse.jpg");
            logitechGProX2.setStockQuantity(15);
            logitechGProX2.setIsAvailable(true);
            mouseRepository.save(logitechGProX2);
            
            // Razer DeathAdder V3 Pro (2025)
            Mouse razerDeathAdderV3 = new Mouse();
            razerDeathAdderV3.setBrand("Razer");
            razerDeathAdderV3.setModel("DeathAdder V3 Pro (2025)");
            razerDeathAdderV3.setDescription("Ultra-lightweight ergonomic wireless gaming mouse with Focus Pro 35K optical sensor.");
            razerDeathAdderV3.setPrice(new BigDecimal("149.99"));
            razerDeathAdderV3.setConnectionType("Wireless");
            razerDeathAdderV3.setDpi(35000);
            razerDeathAdderV3.setSensor("Focus Pro 35K Optical");
            razerDeathAdderV3.setPollingRate(8000);
            razerDeathAdderV3.setButtons(5);
            razerDeathAdderV3.setRgbLighting(false);
            razerDeathAdderV3.setBatteryLife("Up to 90 hours");
            razerDeathAdderV3.setWeight("63 g");
            razerDeathAdderV3.setDimensions("128.0 x 68.0 x 43.0 mm");
            razerDeathAdderV3.setColor("Black");
            razerDeathAdderV3.setErgonomic(true);
            razerDeathAdderV3.setGripType("Palm");
            razerDeathAdderV3.setImagePath("/images/mouse.jpg");
            razerDeathAdderV3.setStockQuantity(12);
            razerDeathAdderV3.setIsAvailable(true);
            mouseRepository.save(razerDeathAdderV3);
            
            // Razer Viper V3 Pro (2025)
            Mouse razerViperV3 = new Mouse();
            razerViperV3.setBrand("Razer");
            razerViperV3.setModel("Viper V3 Pro (2025)");
            razerViperV3.setDescription("Symmetrical ultra-lightweight wireless gaming mouse designed for competitive FPS gaming.");
            razerViperV3.setPrice(new BigDecimal("159.99"));
            razerViperV3.setConnectionType("Wireless");
            razerViperV3.setDpi(35000);
            razerViperV3.setSensor("Focus Pro 35K Optical");
            razerViperV3.setPollingRate(8000);
            razerViperV3.setButtons(6);
            razerViperV3.setRgbLighting(false);
            razerViperV3.setBatteryLife("Up to 95 hours");
            razerViperV3.setWeight("49 g");
            razerViperV3.setDimensions("126.8 x 57.6 x 37.8 mm");
            razerViperV3.setColor("Black");
            razerViperV3.setErgonomic(false);
            razerViperV3.setGripType("Claw/Fingertip");
            razerViperV3.setImagePath("/images/mouse.jpg");
            razerViperV3.setStockQuantity(10);
            razerViperV3.setIsAvailable(true);
            mouseRepository.save(razerViperV3);
            
            // SteelSeries Aerox 5 Wireless (2025)
            Mouse steelseriesAerox5 = new Mouse();
            steelseriesAerox5.setBrand("SteelSeries");
            steelseriesAerox5.setModel("Aerox 5 Wireless (2025)");
            steelseriesAerox5.setDescription("Ultra-lightweight wireless gaming mouse with honeycomb design and customizable RGB lighting.");
            steelseriesAerox5.setPrice(new BigDecimal("139.99"));
            steelseriesAerox5.setConnectionType("Wireless/Bluetooth");
            steelseriesAerox5.setDpi(18000);
            steelseriesAerox5.setSensor("TrueMove Air Optical");
            steelseriesAerox5.setPollingRate(1000);
            steelseriesAerox5.setButtons(9);
            steelseriesAerox5.setRgbLighting(true);
            steelseriesAerox5.setBatteryLife("Up to 180 hours");
            steelseriesAerox5.setWeight("74 g");
            steelseriesAerox5.setDimensions("128.8 x 68.2 x 42.1 mm");
            steelseriesAerox5.setColor("Black");
            steelseriesAerox5.setErgonomic(false);
            steelseriesAerox5.setGripType("Claw");
            steelseriesAerox5.setImagePath("/images/mouse.jpg");
            steelseriesAerox5.setStockQuantity(8);
            steelseriesAerox5.setIsAvailable(true);
            mouseRepository.save(steelseriesAerox5);
            
            // Corsair M75 Air Wireless (2025)
            Mouse corsairM75 = new Mouse();
            corsairM75.setBrand("Corsair");
            corsairM75.setModel("M75 Air Wireless (2025)");
            corsairM75.setDescription("Ultra-lightweight wireless gaming mouse with Marksman optical sensor and ultra-low latency.");
            corsairM75.setPrice(new BigDecimal("149.99"));
            corsairM75.setConnectionType("Wireless/Bluetooth");
            corsairM75.setDpi(26000);
            corsairM75.setSensor("Marksman Optical");
            corsairM75.setPollingRate(2000);
            corsairM75.setButtons(6);
            corsairM75.setRgbLighting(false);
            corsairM75.setBatteryLife("Up to 100 hours");
            corsairM75.setWeight("60 g");
            corsairM75.setDimensions("126.0 x 68.0 x 44.0 mm");
            corsairM75.setColor("Black/White");
            corsairM75.setErgonomic(false);
            corsairM75.setGripType("Claw/Palm");
            corsairM75.setImagePath("/images/mouse.jpg");
            corsairM75.setStockQuantity(14);
            corsairM75.setIsAvailable(true);
            mouseRepository.save(corsairM75);
        }
    }
}