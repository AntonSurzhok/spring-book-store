package mate.academy.bookstore.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cart.AddToCartRequestDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.BookRepository;
import mate.academy.bookstore.repository.CartItemRepository;
import mate.academy.bookstore.repository.ShoppingCartRepository;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCart(String email) {
        ShoppingCart shoppingCart = getCartByEmail(email);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addToCart(
            String email,
            AddToCartRequestDto requestDto
    ) {
        ShoppingCart shoppingCart = getCartByEmail(email);

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found with id: " + requestDto.getBookId()
                ));

        Optional<CartItem> cartItemOptional = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        CartItem cartItem;

        if (cartItemOptional.isEmpty()) {
            cartItem = new CartItem();
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setBook(book);
            cartItem.setQuantity(requestDto.getQuantity());

            shoppingCart.getCartItems().add(cartItem);
        } else {
            cartItem = cartItemOptional.get();
            cartItem.setQuantity(
                    cartItem.getQuantity() + requestDto.getQuantity()
            );
        }

        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItem(
            String email,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    ) {
        ShoppingCart shoppingCart = getCartByEmail(email);

        CartItem cartItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item not found with id: " + cartItemId
                ));

        cartItem.setQuantity(requestDto.getQuantity());

        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartItem(
            String email,
            Long cartItemId
    ) {
        ShoppingCart shoppingCart = getCartByEmail(email);

        CartItem cartItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item not found with id: " + cartItemId
                ));

        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private ShoppingCart getCartByEmail(String email) {
        return shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for user with email: "
                                + email
                ));
    }
}
