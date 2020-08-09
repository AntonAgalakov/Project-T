package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.Views;
import ru.ag.TimeTracker.service.TaskService;
import ru.ag.TimeTracker.service.UserService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user/{userId}/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    @JsonView(Views.IdDescriptionStatus.class)
    public ResponseEntity getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findAll(userId));
    }

    @GetMapping("/{taskId}")
    @JsonView(Views.FullTask.class)
    public ResponseEntity findById(@PathVariable Long userId, @PathVariable Long taskId) {
        Optional<Task> byId = taskService.findById(taskId, userId);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity add(@PathVariable Long userId, @RequestBody Task task) {
        task.setUserId(userId);
        return ResponseEntity.ok(taskService.save(task));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@PathVariable Long userId,
                                 @PathVariable("taskId") Task taskDB,
                                 @RequestBody Task task) {
        task.setUserId(userId);
        BeanUtils.copyProperties(task, taskDB, "id");
        return ResponseEntity.ok(taskService.save(taskDB));
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long userId,
                       @PathVariable("taskId") Task task){
        taskService.delete(task);
    }

}
