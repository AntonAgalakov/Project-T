package ru.ag.TimeTracker.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.model.User;
import ru.ag.TimeTracker.service.UserService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> byId = userService.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") User userDB,
                                 @RequestBody User user) {

        BeanUtils.copyProperties(user, userDB, "id");
        return ResponseEntity.ok(userService.save(userDB));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable User user) {
        userService.delete(user);
    }

}
