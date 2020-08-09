package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.User;
import ru.ag.TimeTracker.model.Views;
import ru.ag.TimeTracker.service.TaskService;
import ru.ag.TimeTracker.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @JsonView(Views.FullInfo.class)
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
    public void delete(@PathVariable("id") User user) {
        List<Task> removeList = taskService.findAll(user.getId());  // find all user tasks
        for (Task item :removeList) {
            taskService.delete(item);                          // and delete
        }
        userService.delete(user);                           // delete the user himself
    }

}
