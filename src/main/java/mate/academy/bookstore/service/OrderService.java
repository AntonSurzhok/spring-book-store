package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.order.CreateOrderRequestDto;
import mate.academy.bookstore.dto.order.OrderDto;
import mate.academy.bookstore.dto.order.OrderItemDto;
import mate.academy.bookstore.dto.order.UpdateOrderStatusRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto createOrder(
            Long userId,
            CreateOrderRequestDto requestDto
    );

    Page<OrderDto> getOrders(
            Long userId,
            Pageable pageable
    );

    Page<OrderItemDto> getOrderItems(
            Long userId,
            Long orderId,
            Pageable pageable
    );

    OrderItemDto getOrderItem(
            Long userId,
            Long orderId,
            Long itemId
    );

    OrderDto updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequestDto requestDto
    );
}
