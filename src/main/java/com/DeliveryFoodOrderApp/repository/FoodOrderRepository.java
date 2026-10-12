package com.DeliveryFoodOrderApp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DeliveryFoodOrderApp.entity.FoodOrder;
import com.DeliveryFoodOrderApp.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    // Fetch items in the same query, since open-in-view is off and lazy loading would fail outside a transaction.
    @EntityGraph(attributePaths = "items")
    List<FoodOrder> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = "items")
    List<FoodOrder> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    @EntityGraph(attributePaths = "items")
    Optional<FoodOrder> findById(Long id);
}