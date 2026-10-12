package com.DeliveryFoodOrderApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DeliveryFoodOrderApp.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}