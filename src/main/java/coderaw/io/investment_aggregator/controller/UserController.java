package coderaw.io.investment_aggregator.controller;

import coderaw.io.investment_aggregator.dto.UserRequestDto;
import coderaw.io.investment_aggregator.dto.UserResponseDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto body) {
        var userId = userService.createUser(body);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userId.toString())).build();
    }

    @GetMapping
    public ResponseEntity<Map<String, List<UserResponseDto>>> findUsers() {
        var users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("userId") String userId) {
        var user = userService.findUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UserRequestDto body) {
        userService.updateUserById(userId, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
