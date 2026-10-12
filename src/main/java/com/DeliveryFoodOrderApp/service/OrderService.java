package com.DeliveryFoodOrderApp.service;

import java.util.List;

import com.DeliveryFoodOrderApp.dto.OrderRequestDTO;
import com.DeliveryFoodOrderApp.dto.OrderResponseDTO;
import com.DeliveryFoodOrderApp.entity.OrderStatus;

public interface OrderService {

    OrderResponseDTO placeOrder(OrderRequestDTO request);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO updateOrderStatus(Long id, OrderStatus newStatus);
}