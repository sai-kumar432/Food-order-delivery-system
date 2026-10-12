package com.DeliveryFoodOrderApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    @NotNull(message = "Menu item id is required")
    private Long menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}