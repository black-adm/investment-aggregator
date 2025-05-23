package coderaw.io.investment_aggregator.controller;

import coderaw.io.investment_aggregator.dto.CreateUserDto;
import coderaw.io.investment_aggregator.entity.User;
import coderaw.io.investment_aggregator.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto body) {
        var userId = userService.createUser(body);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        return null;
    }
}
