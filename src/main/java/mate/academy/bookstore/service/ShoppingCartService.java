package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemRequestDto;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String email);

    ShoppingCartDto addBookToShoppingCart(String email, Long bookId);

    ShoppingCartDto updateCartItem(
            String email,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    );

    void deleteCartItem(String email, Long cartItemId);

    ShoppingCart createShoppingCart(User user);
}
