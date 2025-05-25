package coderaw.io.investment_aggregator.service;

import coderaw.io.investment_aggregator.dto.UserRequestDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {
            var user = new User(
                    "john.doe",
                    "johndoe@example.com",
                    "admin1234"
            );

            user.setUserId(UUID.randomUUID());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new UserRequestDto(
                    "john.doe",
                    "johndoe@example.com",
                    "admin1234"
            );

            UUID output = userService.createUser(input);
            assertNotNull(output);

            var capturedValue = userArgumentCaptor.getValue();
            assertEquals(input.username(), capturedValue.getUsername());
            assertEquals(input.email(), capturedValue.getEmail());
            assertEquals(input.password(), capturedValue.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            doThrow(new RuntimeException())
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            var input = new UserRequestDto(
                    "john.doe",
                    "johndoe@example.com",
                    "admin1234"
            );

            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }
}