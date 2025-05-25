package coderaw.io.investment_aggregator.service;

import coderaw.io.investment_aggregator.dto.UserRequestDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user with success")
        void shoudlCreateAUserWithSuccess() {
            var user = new User("john.doe", "johndoe@example.com", "admin1234");
            user.setUserId(UUID.randomUUID());

            doReturn(user).when(userRepository).save(any());

            var input = new UserRequestDto("john.doe", "johndoe@example.com", "admin1234");
            UUID output = userService.createUser(input);

            assertNotNull(output);
        }
    }
}