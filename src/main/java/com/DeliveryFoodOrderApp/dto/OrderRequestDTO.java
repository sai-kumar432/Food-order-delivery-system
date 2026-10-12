package com.DeliveryFoodOrderApp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemDTO> items;
}