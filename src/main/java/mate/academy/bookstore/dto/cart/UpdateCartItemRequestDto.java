package mate.academy.bookstore.dto.cart;

import jakarta.validation.constraints.Min;

public class UpdateCartItemRequestDto {

    @Min(1)
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
