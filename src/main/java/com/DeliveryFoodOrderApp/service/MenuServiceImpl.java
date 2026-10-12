package com.DeliveryFoodOrderApp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeliveryFoodOrderApp.entity.MenuItem;
import com.DeliveryFoodOrderApp.repository.MenuItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> getAvailableMenu() {
        return menuItemRepository.findByAvailableTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> getFullMenu() {
        return menuItemRepository.findAll();
    }
}