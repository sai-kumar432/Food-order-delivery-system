package com.DeliveryFoodOrderApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DeliveryFoodOrderApp.entity.MenuItem;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByAvailableTrue();
}