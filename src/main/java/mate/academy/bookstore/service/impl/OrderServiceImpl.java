package mate.academy.bookstore.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.order.CreateOrderRequestDto;
import mate.academy.bookstore.dto.order.OrderDto;
import mate.academy.bookstore.dto.order.OrderItemDto;
import mate.academy.bookstore.dto.order.UpdateOrderStatusRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.OrderItemMapper;
import mate.academy.bookstore.mapper.OrderMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.Order;
import mate.academy.bookstore.model.OrderItem;
import mate.academy.bookstore.model.OrderStatus;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.repository.BookRepository;
import mate.academy.bookstore.repository.OrderItemRepository;
import mate.academy.bookstore.repository.OrderRepository;
import mate.academy.bookstore.repository.ShoppingCartRepository;
import mate.academy.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto createOrder(
            Long userId,
            CreateOrderRequestDto requestDto
    ) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart for user with id "
                                + userId + " not found"
                ));

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot create an order from an empty cart"
            );
        }

        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(BigDecimal.ZERO);
        order.setOrderItems(new HashSet<>());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Book book = bookRepository.findById(cartItem.getBook().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Book with id "
                                    + cartItem.getBook().getId()
                                    + " not found"
                    ));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(book.getPrice());

            order.getOrderItems().add(orderItem);

            BigDecimal itemTotal = book.getPrice()
                    .multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    );

            total = total.add(itemTotal);
        }

        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);

        shoppingCart.clearCart();

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getOrders(
            Long userId,
            Pageable pageable
    ) {
        return orderRepository
                .findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemDto> getOrderItems(
            Long userId,
            Long orderId,
            Pageable pageable
    ) {
        verifyOrderOwnership(orderId, userId);

        return orderItemRepository
                .findAllByOrderIdAndOrderUserId(
                        orderId,
                        userId,
                        pageable
                )
                .map(orderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDto getOrderItem(
            Long userId,
            Long orderId,
            Long itemId
    ) {
        return orderItemRepository
                .findByIdAndOrderIdAndOrderUserId(
                        itemId,
                        orderId,
                        userId
                )
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order item with id "
                                + itemId
                                + " not found"
                ));
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequestDto requestDto
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order with id "
                                + orderId
                                + " not found"
                ));

        order.setStatus(requestDto.getStatus());

        return orderMapper.toDto(order);
    }

    private void verifyOrderOwnership(
            Long orderId,
            Long userId
    ) {
        orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order with id "
                                + orderId
                                + " not found"
                ));
    }
}
