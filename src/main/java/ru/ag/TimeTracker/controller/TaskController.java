package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ag.TimeTracker.exception.*;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.model.TimeConverter;
import ru.ag.TimeTracker.model.TimeTrack;
import ru.ag.TimeTracker.model.Views;
import ru.ag.TimeTracker.service.TaskService;
import ru.ag.TimeTracker.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/user/{userId}/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    final static Logger logger = Logger.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /* Search function for all the user's tasks. (returns List) */

    @GetMapping
    @JsonView(Views.IdDescriptionStatus.class)
    public ResponseEntity getAll(@PathVariable Long userId) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        return ResponseEntity.ok(taskService.findAllByUserId(userId));
    }

    /* Search function for the user's task.  */

    @GetMapping("/{taskId}")
    @JsonView(Views.FullTask.class)
    public ResponseEntity findById(@PathVariable Long userId, @PathVariable Long taskId) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        Optional<Task> byId = taskService.findById(taskId, userId);
        if(!byId.isPresent()) {
            logger.error("Task (" + taskId + ") does not found");
            throw new TaskNotFoundException(taskId);
        }
        return ResponseEntity.ok(byId);
    }

    /* Function creating a task without automatic time initialization */

    @PostMapping
    public ResponseEntity add(@PathVariable Long userId, @RequestBody Task task) {
        if (!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }

        if (task.getDescription() == null) {
            logger.error("In created task does not description");
            throw new TaskNotDescriptionException();
        }

        task.setUserId(userId);
        logger.info("User's [" + task.getUserId() + "] task created");
        return ResponseEntity.ok(taskService.save(task));
    }

    /* Create and start a task function */

    @PostMapping("/start")
    public ResponseEntity startTask(@PathVariable Long userId, @RequestBody Task task) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        if(task.getDescription() == null) {
            logger.error("In created task does not description.");
            throw new TaskNotDescriptionException();
        }
        task.setDateStart(new Date());
        task.setUserId(userId);
        task.setStatus("Start");
        logger.info("User's [" + task.getUserId() + "] task created and started");
        return ResponseEntity.ok(taskService.save(task));
    }

    /* Task completion function */

    @PutMapping("/{taskId}/finish")
    public ResponseEntity finishTask(@PathVariable Long userId,
                                     @PathVariable("taskId") Task taskDB) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        if(taskDB == null) {
            logger.error("Task does not exist.");
            throw new TaskNotFoundException();
        }
        if(taskDB.getDateStart() == null) {
            logger.error("Task [" + taskDB.getId() + "] will not finish, because it is not start date!");
            throw new TaskNotStartDateException(taskDB.getId());
        }
        if(taskDB.getStatus().equals("Finish")) {
            logger.error("Task [" + taskDB.getId() + "] finished already.");
            throw new TaskAlreadyFinishedException(taskDB.getId());
        }
        Task task = new Task();
        BeanUtils.copyProperties(taskDB, task);
        task.setDateEnd(new Date());
        task.setStatus("Finish");
        logger.info("Task [" + taskDB.getId() + "] finished");
        return ResponseEntity.ok(taskService.save(task));
    }

    /* Task update function */

    @PutMapping("/{taskId}")
    public ResponseEntity update(@PathVariable Long userId,
                                 @PathVariable("taskId") Task taskDB,
                                 @RequestBody Task task) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        if(taskDB == null) {
            logger.error("The updated task does not exist");
            throw new TaskNotFoundException();
        }
        task.setUserId(userId);
        if(task.getStatus() == null) {
            task.setStatus(taskDB.getStatus());
        }
        if(task.getDescription() == null) {
            task.setDescription(taskDB.getDescription());
        }
        if(task.getDateStart() == null) {
            task.setDateStart(taskDB.getDateStart());
        }
        BeanUtils.copyProperties(task, taskDB, "id");

        logger.info("Task ["+task.getUserId()+"] updated");

        return ResponseEntity.ok(taskService.save(taskDB));
    }

    /* A function that shows all the user's work over a period of time */

    @GetMapping("/works/{fromDay}/{toDay}")
    @JsonView(Views.QuestionOne.class)
    public ResponseEntity works(@PathVariable Long userId,
                                     @PathVariable String fromDay,
                                     @PathVariable String toDay) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        TimeConverter convert = new TimeConverter();
        List<Task> taskList = taskService.findLaborCosts(userId,
                                                         convert.strToDate(fromDay),
                                                         convert.strToDate(toDay));
        if(taskList.size() == 0) {
            logger.error("Intervals works. List of task does not exist");
        }
        ArrayList<TimeTrack> timeTracks = new ArrayList<>();
        for (Task task : taskList) {
            Long minuts = convert.minuts(task);
            timeTracks.add(new TimeTrack(task.getId(), minuts));
        }
        logger.info("View all the user's labor costs for the period [" +fromDay +";" + toDay+ "]");
        return ResponseEntity.ok(timeTracks);
    }

    /* Function all time intervals occupied by work over a period of time */

    @GetMapping("/intervals_works/{fromDay}/{toDay}")
    @JsonView(Views.QuestionTwo.class)
    public ResponseEntity intervalsWorks(@PathVariable Long userId,
                                         @PathVariable String fromDay,
                                         @PathVariable String toDay) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }

        TimeConverter convert = new TimeConverter();
        List<Task> taskList = taskService.findLaborCosts(userId,
                                                         convert.strToDate(fromDay),
                                                         convert.strToDate(toDay));
        if(taskList.size() == 0) {
            logger.error("Intervals works. List of task does not exist");
            throw new TaskNotFoundException();
        }
        ArrayList<TimeTrack> timeTracks = new ArrayList<>();
        for (Task task : taskList) {
            TimeTrack timeTrack = new TimeTrack(task.getId(),
                                                task.getDateStart(),
                                                task.getDateEnd());
            timeTracks.add(timeTrack);
        }
        logger.info("View all time intervals occupied by work during the period [" + fromDay + ";" + toDay + "]");
        return ResponseEntity.ok(timeTracks);
    }

    /* A function that shows the amount of work spent on all issues over a period of time */

    @GetMapping("/work_time/{fromDay}/{toDay}")
    @JsonView(Views.QuestionThree.class)
    public ResponseEntity workTime(@PathVariable Long userId,
                                   @PathVariable String fromDay,
                                   @PathVariable String toDay) {
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        TimeConverter convert = new TimeConverter();
        List<Task> taskList = taskService.findLaborCosts(userId,
                                                         convert.strToDate(fromDay),
                                                         convert.strToDate(toDay));
        if(taskList.size() == 0) {
            logger.error("Intervals works. List of task does not exist");
        }
        TimeTrack timeTrack = new TimeTrack();
        Long time = 0L;
        for (Task task : taskList) {
            time += convert.minuts(task);
            timeTrack.setAllTime(time);
        }
        logger.info("Viewing the amount of labor costs");
        return ResponseEntity.ok(timeTrack);
    }

    /* Delete task function */

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long userId, @PathVariable("taskId") Task task){
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        if(task == null) {
            logger.error("Task does not exist");
            throw new TaskNotFoundException();
        }
        logger.info("Task [" + task.getId() + "] deleted");
        taskService.delete(task);
    }

    /* Function for deleting all user tasks */

    @DeleteMapping
    public void deleteAll(@PathVariable Long userId){
        if(!userService.existsById(userId)) {
            logger.error("User (" + userId + ") does not exist");
            throw new UserNotFoundException(userId);
        }
        List<Task> removeList = taskService.findAllByUserId(userId);
        if(removeList.size() == 0) {
            logger.error("List of task is null");
            throw new TaskNotFoundException();
        }
        for(Task task : removeList) {
            taskService.delete(task);
        }
        logger.info("User's [" + userId + "] tasks deleted");
    }

}
