package ru.ag.TaskTracker.service;

import ru.ag.TaskTracker.models.Task;
import ru.ag.TaskTracker.models.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getTask(Long id);

    Task saveTask(Task task);

    List<Task> findAll();

}
