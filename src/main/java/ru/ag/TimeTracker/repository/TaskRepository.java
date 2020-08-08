package ru.ag.TimeTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndUserId(Long id, User user);

    List<Task> findAllByAndUserId(User user);

}
