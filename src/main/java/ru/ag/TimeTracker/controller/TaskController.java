package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.Views;
import ru.ag.TimeTracker.service.TaskService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @JsonView(Views.IdDescriptionStatus.class)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Task> byId = taskService.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Task taskDB,
                                      @RequestBody Task task) {

        BeanUtils.copyProperties(task, taskDB, "id");
        return ResponseEntity.ok(taskService.save(taskDB));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Task task) {
        taskService.delete(task);
    }
}
