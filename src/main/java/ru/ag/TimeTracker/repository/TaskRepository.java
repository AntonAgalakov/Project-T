package ru.ag.TimeTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ag.TimeTracker.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
