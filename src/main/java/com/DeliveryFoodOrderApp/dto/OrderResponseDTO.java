package com.DeliveryFoodOrderApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.DeliveryFoodOrderApp.entity.OrderStatus;

public record OrderResponseDTO(
        Long id,
        String customerName,
        String deliveryAddress,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items
) {}