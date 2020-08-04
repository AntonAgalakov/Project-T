package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.Views;
import ru.ag.TimeTracker.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    @JsonView(Views.IdDescriptionStatus.class)
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable("id") Task task) {
        return task;
    }

    @PostMapping
    public Task add(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable("id") Task taskDB,
                                      @RequestBody Task task) {

        BeanUtils.copyProperties(task, taskDB, "id");
        return taskRepository.save(taskDB);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Task task) {
        taskRepository.delete(task);
    }
}
