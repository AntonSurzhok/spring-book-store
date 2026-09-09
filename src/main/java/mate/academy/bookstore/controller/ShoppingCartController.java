package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cart.AddToCartRequestDto;
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
@RequestMapping("/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(
        name = "Shopping Cart",
        description = "Shopping cart management endpoints"
)
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get current user's shopping cart")
    public ShoppingCartDto getShoppingCart(
            Authentication authentication
    ) {
        return shoppingCartService.getShoppingCart(
                authentication.getName()
        );
    }

    @PostMapping
    @Operation(summary = "Add a book to the shopping cart")
    public ShoppingCartDto addToCart(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequestDto requestDto
    ) {
        return shoppingCartService.addToCart(
                authentication.getName(),
                requestDto
        );
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update cart item quantity")
    public ShoppingCartDto updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.updateCartItem(
                authentication.getName(),
                cartItemId,
                requestDto
        );
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove an item from the shopping cart")
    public void deleteCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId
    ) {
        shoppingCartService.deleteCartItem(
                authentication.getName(),
                cartItemId
        );
    }
}
