package ru.ag.TimeTracker.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.User;
import ru.ag.TimeTracker.service.TaskService;
import ru.ag.TimeTracker.service.UserService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user/{id}/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAll(@PathVariable("id") User user) {
        return ResponseEntity.ok(taskService.findAll(user));
    }

    @GetMapping("/{task_id}")
    public ResponseEntity findById(@PathVariable("id") User user, @PathVariable Long task_id) {
        Optional<Task> byId = taskService.findById(task_id, user);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    @PostMapping()
    public ResponseEntity add(@PathVariable("id") User user, @RequestBody Task task) {
        task.setUserId(user);
        user.addTask(task);
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{task_id}")
    public ResponseEntity update(@PathVariable("id") User user,
                                 @PathVariable("task_id") Task taskDB,
                                 @RequestBody Task task) {

        BeanUtils.copyProperties(task, taskDB, "id", "responsibleUser");
        return ResponseEntity.ok(taskService.save(taskDB));
    }

    @DeleteMapping("/{task_id}")
    public void delete(@PathVariable("id") User user,
                       @PathVariable Task task){
        user.removeTask(task);
        taskService.delete(task);
    }

}
