package coderaw.io.investment_aggregator.service;

import coderaw.io.investment_aggregator.dto.CreateUserDto;
import coderaw.io.investment_aggregator.dto.UserResponseDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, List<UserResponseDto>> findAllUsers() {
        var users = userRepository.findAll();

         List<UserResponseDto> allUsers = users
                                            .stream()
                                            .map(u -> new UserResponseDto(
                                                u.getUserId().toString(),
                                                u.getUsername(),
                                                u.getEmail(),
                                                u.getCreationTimeStamp()
                                            )).toList();

         return Collections.singletonMap("users", allUsers);
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

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);
        var userAlreadyExists = userRepository.existsById(id);

        if (userAlreadyExists) {
            userRepository.deleteById(id);
        }
    }
}
