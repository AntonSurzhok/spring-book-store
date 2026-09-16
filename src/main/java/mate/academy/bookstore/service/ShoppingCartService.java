package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cart.AddToCartRequestDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemRequestDto;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(Long userId);

    ShoppingCartDto addToCart(
            Long userId,
            AddToCartRequestDto requestDto
    );

    ShoppingCartDto updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    );

    void deleteCartItem(Long userId, Long cartItemId);

    void clearCart(Long userId);

    ShoppingCart createShoppingCart(User user);
}
