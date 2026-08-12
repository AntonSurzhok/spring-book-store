package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.user.UserRegistrationRequestDto;
import mate.academy.bookstore.dto.user.UserResponseDto;
import mate.academy.bookstore.exception.RegistrationException;
import mate.academy.bookstore.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account"
    )
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request
    ) throws RegistrationException {
        return authenticationService.register(request);
    }
}
