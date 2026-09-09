package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cart.AddToCartRequestDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemRequestDto;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String email);

    ShoppingCartDto addToCart(
            String email,
            AddToCartRequestDto requestDto
    );

    ShoppingCartDto updateCartItem(
            String email,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    );

    void deleteCartItem(String email, Long cartItemId);
}
