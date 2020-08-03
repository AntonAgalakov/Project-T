package ru.ag.TaskTracker.service;

import ru.ag.TaskTracker.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(Long id);

    User saveUser(User user);

    List<User> findAll();
}
