package ru.ag.TimeTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findById(Long taskId, Long userId) {
        return taskRepository.findByIdAndUserId(taskId, userId);
    }

    public List<Task> findAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findAllByUserIdAndArea(Long userId, String area) {
        return taskRepository.findAddByUserIdAndArea(userId, area);
    }

    public List<Task> findLaborCosts(Long userId, Date dateStart, Date dateEnd) {
       List<Task> taskList = taskRepository.findAllByUserIdAndDateStartBetweenAndDateEndBetween(userId, dateStart, dateEnd,dateStart, dateEnd);
       return taskList;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public boolean existsById(Long id){
        return taskRepository.existsById(id);
    }

    public boolean existsByIdAndArea(Long userId, String area){
        return taskRepository.existsByUserIdAndArea(userId, area);
    }
}
