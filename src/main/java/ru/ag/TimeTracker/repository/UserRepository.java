package ru.ag.TimeTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ag.TimeTracker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByName(String name);

    boolean existsById(Long id);

    User findByName(String name);

    void deleteAll();

}
