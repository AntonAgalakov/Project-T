package ru.ag.TimeTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.User;
import ru.ag.TimeTracker.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskUserRepository;

    @Autowired
    public TaskService(TaskRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    public Optional<Task> findById(Long id, User user) {
        return taskUserRepository.findByIdAndUserId(id, user);
    }

    public List<Task> findAll(User user) {
        return taskUserRepository.findAllByAndUserId(user);
    }

    public Task save(Task task) {
        return taskUserRepository.save(task);
    }

    public void delete(Task task) {
        taskUserRepository.delete(task);
    }
}
