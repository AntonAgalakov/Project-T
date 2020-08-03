package ru.ag.TaskTracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ag.TaskTracker.models.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
