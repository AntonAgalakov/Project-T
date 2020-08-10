package ru.ag.TimeTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ag.TimeTracker.model.User;
import ru.ag.TimeTracker.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean existsByName(String name) {
        return  userRepository.existsByName(name);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
