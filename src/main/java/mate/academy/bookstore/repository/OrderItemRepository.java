package mate.academy.bookstore.repository;

import java.util.Optional;
import mate.academy.bookstore.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findAllByOrderIdAndOrderUserId(
            Long orderId,
            Long userId,
            Pageable pageable
    );

    Optional<OrderItem> findByIdAndOrderIdAndOrderUserId(
            Long itemId,
            Long orderId,
            Long userId
    );
}
