package ru.ag.TimeTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ag.TimeTracker.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
