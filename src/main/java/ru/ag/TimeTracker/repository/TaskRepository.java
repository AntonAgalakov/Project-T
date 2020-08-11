package ru.ag.TimeTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ag.TimeTracker.model.Task;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserId(Long userId);
    
    List<Task> findAllByUserIdAndDateStartBetweenAndDateEndBetween(Long userId,
                                                                   Date dateStart, Date dateEnd,
                                                                   Date dateStart2, Date dateEnd2);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

    boolean existsById(Long id);


}
