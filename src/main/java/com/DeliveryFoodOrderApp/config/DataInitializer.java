package com.DeliveryFoodOrderApp.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.DeliveryFoodOrderApp.entity.MenuItem;
import com.DeliveryFoodOrderApp.repository.MenuItemRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadMenu(MenuItemRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;   // don't duplicate data
            }
            repository.saveAll(List.of(
                    item("Margherita Pizza", "Classic cheese and tomato", "249.00", true),
                    item("Veg Burger", "Crispy patty with fresh veggies", "129.00", true),
                    item("Paneer Wrap", "Spiced paneer in a soft wrap", "149.00", true),
                    item("Veg Biryani", "Fragrant basmati rice with vegetables", "189.00", true),
                    item("Masala Dosa", "Crisp dosa with potato filling", "99.00", true),
                    item("French Fries", "Salted and crispy", "89.00", true),
                    item("Chocolate Shake", "Thick and creamy", "119.00", false)
            ));
        };
    }

    private MenuItem item(String name, String description, String price, boolean available) {
        return MenuItem.builder()
                .name(name)
                .description(description)
                .price(new BigDecimal(price))
                .available(available)
                .build();
    }
}