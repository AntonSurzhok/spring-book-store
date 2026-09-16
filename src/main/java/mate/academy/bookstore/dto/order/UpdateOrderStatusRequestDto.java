package mate.academy.bookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mate.academy.bookstore.model.OrderStatus;

@Getter
@Setter
public class UpdateOrderStatusRequestDto {

    @NotNull
    private OrderStatus status;
}
