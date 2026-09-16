package mate.academy.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequestDto {

    @NotBlank
    private String shippingAddress;
}
