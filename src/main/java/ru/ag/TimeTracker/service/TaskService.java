package ru.ag.TimeTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
