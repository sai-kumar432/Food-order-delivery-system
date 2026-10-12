package com.DeliveryFoodOrderApp.service;

import java.util.List;

import com.DeliveryFoodOrderApp.entity.MenuItem;

public interface MenuService {
    List<MenuItem> getAvailableMenu();
    List<MenuItem> getFullMenu();
}