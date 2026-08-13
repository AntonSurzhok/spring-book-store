package mate.academy.bookstore.service;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.user.UserRegistrationRequestDto;
import mate.academy.bookstore.dto.user.UserResponseDto;
import mate.academy.bookstore.exception.RegistrationException;
import mate.academy.bookstore.mapper.UserMapper;
import mate.academy.bookstore.model.Role;
import mate.academy.bookstore.model.RoleName;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.RoleRepository;
import mate.academy.bookstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(
            UserRegistrationRequestDto requestDto
    ) {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException(
                    "User with email " + requestDto.email()
                            + " already exists"
            );
        }

        User user = userMapper.toModel(requestDto);

        user.setPassword(
                passwordEncoder.encode(requestDto.password())
        );

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RegistrationException(
                        "USER role not found"
                ));

        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
