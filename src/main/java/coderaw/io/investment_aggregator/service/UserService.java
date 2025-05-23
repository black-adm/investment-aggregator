package coderaw.io.investment_aggregator.service;

import coderaw.io.investment_aggregator.dto.UserRequestDto;
import coderaw.io.investment_aggregator.dto.UserResponseDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(UserRequestDto user) {
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

    public void updateUserById(String userId, UserRequestDto updateUser) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) return;

        var user = userEntity.get();

        if (updateUser.username() != null) {
            user.setUsername(updateUser.username());
        }

        if (updateUser.email() != null) {
            user.setEmail(updateUser.email());
        }

        if (updateUser.password() != null) {
            user.setPassword(updateUser.password());
        }

        userRepository.save(user);
    }

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);
        var userAlreadyExists = userRepository.existsById(id);

        if (userAlreadyExists) {
            userRepository.deleteById(id);
        }
    }
}
