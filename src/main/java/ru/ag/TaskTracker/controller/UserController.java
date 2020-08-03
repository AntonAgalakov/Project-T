package ru.ag.TaskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TaskTracker.models.User;
import ru.ag.TaskTracker.service.UserService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        return Objects.isNull(user)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(user);
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

}
