package com.DeliveryFoodOrderApp.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DeliveryFoodOrderApp.dto.OrderItemDTO;
import com.DeliveryFoodOrderApp.dto.OrderItemResponseDTO;
import com.DeliveryFoodOrderApp.dto.OrderRequestDTO;
import com.DeliveryFoodOrderApp.dto.OrderResponseDTO;
import com.DeliveryFoodOrderApp.entity.FoodOrder;
import com.DeliveryFoodOrderApp.entity.MenuItem;
import com.DeliveryFoodOrderApp.entity.OrderItem;
import com.DeliveryFoodOrderApp.entity.OrderStatus;
import com.DeliveryFoodOrderApp.exception.BadRequestException;
import com.DeliveryFoodOrderApp.exception.ResourceNotFoundException;
import com.DeliveryFoodOrderApp.repository.FoodOrderRepository;
import com.DeliveryFoodOrderApp.repository.MenuItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final FoodOrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {
        FoodOrder order = FoodOrder.builder()
                .customerName(request.getCustomerName().trim())
                .deliveryAddress(request.getDeliveryAddress().trim())
                .status(OrderStatus.PENDING)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDTO itemDto : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Menu item not found with id " + itemDto.getMenuItemId()));

            if (!menuItem.isAvailable()) {
                throw new BadRequestException("'" + menuItem.getName() + "' is currently unavailable");
            }

            // Snapshot the price at order time
            OrderItem orderItem = OrderItem.builder()
                    .menuItem(menuItem)
                    .quantity(itemDto.getQuantity())
                    .itemPrice(menuItem.getPrice())
                    .build();
            order.addItem(orderItem);

            total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        order.setTotalAmount(total);

        // Cascade.ALL on items means saving the order saves its items too
        FoodOrder saved = orderRepository.save(order);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        return toResponse(findOrderOrThrow(id));
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus newStatus) {
        FoodOrder order = findOrderOrThrow(id);

        // Only allow forward movement: PENDING -> CONFIRMED -> DELIVERED
        if (newStatus.ordinal() != order.getStatus().ordinal() + 1) {
            throw new BadRequestException("Invalid status change: "
                    + order.getStatus() + " -> " + newStatus);
        }

        order.setStatus(newStatus);
        // No explicit save needed: the entity is managed inside the transaction, so dirty checking flushes the change on commit.
        return toResponse(order);
    }

    // ---------- helpers ----------

    private FoodOrder findOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

    private OrderResponseDTO toResponse(FoodOrder order) {
        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(i -> new OrderItemResponseDTO(
                        i.getMenuItem().getId(),
                        i.getMenuItem().getName(),
                        i.getQuantity(),
                        i.getItemPrice(),
                        i.getItemPrice().multiply(BigDecimal.valueOf(i.getQuantity()))))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerName(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items);
    }
}