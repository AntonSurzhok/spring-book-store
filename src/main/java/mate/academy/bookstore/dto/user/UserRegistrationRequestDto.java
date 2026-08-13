package mate.academy.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mate.academy.bookstore.validation.FieldMatch;

@FieldMatch(
        first = "password",
        second = "repeatPassword",
        message = "Passwords do not match"
)
public record UserRegistrationRequestDto(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        String repeatPassword,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        String shippingAddress
) {
}
