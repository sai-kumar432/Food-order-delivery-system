package com.DeliveryFoodOrderApp.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long menuItemId,
        String menuItemName,
        int quantity,
        BigDecimal itemPrice,
        BigDecimal lineTotal
) {}