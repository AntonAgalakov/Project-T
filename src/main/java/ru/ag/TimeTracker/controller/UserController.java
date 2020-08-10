package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.exception.UserNotFoundException;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.User;
import ru.ag.TimeTracker.model.Views;
import ru.ag.TimeTracker.service.TaskService;
import ru.ag.TimeTracker.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    final static Logger logger = Logger.getLogger(TaskController.class);

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    /* Function of finding all User  (Returns List) */

    @GetMapping
    @JsonView(Views.IdName.class)
    public ResponseEntity getAll() {
        List<User> userList= userService.findAll();
        if(userList.size() == 0) {
            logger.error("No users");
            throw new UserNotFoundException("No users");
        }
        return ResponseEntity.ok(userList);
    }

    /* Function of finding User by Id */

    @GetMapping("/{id}")
    @JsonView(Views.FullInfo.class)
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> byId = userService.findById(id);
        if(!byId.isPresent()) {
            logger.error("User [" + id + "] does not exist");
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.ok(byId);
    }

    /* New user creation function */

    @PostMapping
    public ResponseEntity add(@RequestBody User user) {
        if(userService.existsByName(user.getName())) {
            logger.error("The user [" + user.getName() + "] already created");
        }
        logger.info("User [" + user.getName() + "] created");
        return ResponseEntity.ok(userService.save(user));
    }

    /* User update function */

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") User userDB,
                                 @RequestBody User user) {

        BeanUtils.copyProperties(user, userDB, "id");
        logger.info("User [" + user.getName() +"] updated");
        return ResponseEntity.ok(userService.save(userDB));
    }

    /* Function of deleting User and all its tasks */

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") User user) {
        if(!userService.existsByName(user.getName())){
            logger.error("Error delete user");
        }
        List<Task> removeList = taskService.findAll(user.getId());
        for (Task item :removeList) {
            taskService.delete(item);
        }
        logger.info("User["+ user.getName()+"] deleted");
        userService.delete(user);
    }

}
