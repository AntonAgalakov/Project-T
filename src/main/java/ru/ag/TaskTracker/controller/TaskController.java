package ru.ag.TaskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TaskTracker.models.Task;
import ru.ag.TaskTracker.service.TaskService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTask(id);
        return Objects.isNull(task)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(task);
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.saveTask(task));
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }
}
