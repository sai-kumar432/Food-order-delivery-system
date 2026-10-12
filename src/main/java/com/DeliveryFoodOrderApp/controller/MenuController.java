package com.DeliveryFoodOrderApp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DeliveryFoodOrderApp.entity.MenuItem;
import com.DeliveryFoodOrderApp.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // Customer-facing: only available items
    @GetMapping
    public List<MenuItem> getAvailableMenu() {
        return menuService.getAvailableMenu();
    }

    // Admin-facing: everything, including unavailable items
    @GetMapping("/all")
    public List<MenuItem> getFullMenu() {
        return menuService.getFullMenu();
    }
}