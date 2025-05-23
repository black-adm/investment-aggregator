package coderaw.io.investment_aggregator.service;

import coderaw.io.investment_aggregator.dto.CreateUserDto;
import coderaw.io.investment_aggregator.dto.UserResponseDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto user) {
        var entity = new User(user.username(), user.email(), user.password());
        var data = userRepository.save(entity);

        return data.getUserId();
    }

    public Optional<UserResponseDto> findUserById(String userId) {
        var user = userRepository.findById(UUID.fromString(userId));

        return user.map(u -> new UserResponseDto(
                u.getUserId().toString(),
                u.getUsername(),
                u.getEmail(),
                u.getCreationTimeStamp()
        ));
    }
}
