package mate.academy.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemRequestDto;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        return shoppingCartService.getShoppingCart(authentication.getName());
    }

    @PostMapping("/items/{bookId}")
    public ShoppingCartDto addBookToShoppingCart(
            @PathVariable Long bookId,
            Authentication authentication
    ) {
        return shoppingCartService.addBookToShoppingCart(
                authentication.getName(),
                bookId
        );
    }

    @PutMapping("/items/{cartItemId}")
    public ShoppingCartDto updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto,
            Authentication authentication
    ) {
        return shoppingCartService.updateCartItem(
                authentication.getName(),
                cartItemId,
                requestDto
        );
    }

    @DeleteMapping("/items/{cartItemId}")
    public void deleteCartItem(
            @PathVariable Long cartItemId,
            Authentication authentication
    ) {
        shoppingCartService.deleteCartItem(
                authentication.getName(),
                cartItemId
        );
    }
}
