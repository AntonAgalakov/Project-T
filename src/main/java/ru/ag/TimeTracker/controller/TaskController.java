package ru.ag.TimeTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /* Search function for all the user's tasks. (returns List) */

    @GetMapping
    @JsonView(Views.IdDescriptionStatus.class)
    public ResponseEntity getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findAll(userId));
    }

    /* Search function for the user's task.  */

    @GetMapping("/{taskId}")
    @JsonView(Views.FullTask.class)
    public ResponseEntity findById(@PathVariable Long userId, @PathVariable Long taskId) {
        Optional<Task> byId = taskService.findById(taskId, userId);

        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    /* Function creating a task without automatic time initialization */

    @PostMapping
    public ResponseEntity add(@PathVariable Long userId, @RequestBody Task task) {
        task.setUserId(userId);

        return ResponseEntity.ok(taskService.save(task));
    }

    /* Create and start a task function */

    @PostMapping("/start")
    public ResponseEntity startTask(@PathVariable Long userId, @RequestBody Task task) {
        task.setDateStart(new Date());
        task.setUserId(userId);
        task.setStatus("Start");

        return ResponseEntity.ok(taskService.save(task));
    }

    /* Task completion function */

    @PutMapping("/{taskId}/finish")
    public ResponseEntity finishTask(@PathVariable Long userId,
                                 @PathVariable("taskId") Task taskDB) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDB, task);
        task.setDateEnd(new Date());
        task.setStatus("Finish");

        return ResponseEntity.ok(taskService.save(task));
    }

    /* Task update function */

    @PutMapping("/{taskId}")
    public ResponseEntity update(@PathVariable Long userId,
                                 @PathVariable("taskId") Task taskDB,
                                 @RequestBody Task task) {
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

        return ResponseEntity.ok(taskService.save(taskDB));
    }

    /* A function that shows all the user's work over a period of time */

    @GetMapping("/works/{fromDay}/{toDay}")
    @JsonView(Views.QuestionOne.class)
    public ResponseEntity laborCosts(@PathVariable Long userId,
                                     @PathVariable String fromDay,
                                     @PathVariable String toDay) {
        TimeConverter convert = new TimeConverter();
        List<Task> temp = taskService.findLaborCosts(userId, convert.strToDate(fromDay), convert.strToDate(toDay));
        ArrayList<TimeTrack> timeTracks = new ArrayList<>();
        for (Task task : temp) {
            TimeTrack timeTrack = new TimeTrack();
            timeTrack.setTaskId(task.getId());
            Long date = convert.minuts(task);
            timeTrack.setTime(date);
            timeTracks.add(timeTrack);
        }
        return ResponseEntity.ok(timeTracks);
    }

    /* Function all time intervals occupied by work over a period of time */

    @GetMapping("/intervals_works/{fromDay}/{toDay}")
    @JsonView(Views.QuestionTwo.class)
    public ResponseEntity laborTime(@PathVariable Long userId,
                                     @PathVariable String fromDay,
                                     @PathVariable String toDay) {
        TimeConverter convert = new TimeConverter();
        List<Task> temp = taskService.findLaborCosts(userId, convert.strToDate(fromDay), convert.strToDate(toDay));
        ArrayList<TimeTrack> timeTracks = new ArrayList<>();
        for (Task task : temp) {
            TimeTrack timeTrack = new TimeTrack();
            timeTrack.setTaskId(task.getId());
            timeTrack.setDateStart(task.getDateStart());
            timeTrack.setDateStart(task.getDateEnd());
            timeTracks.add(timeTrack);
        }
        return ResponseEntity.ok(timeTracks);
    }

    /* A function that shows the amount of work spent on all issues over a period of time */

    @GetMapping("/work_time/{fromDay}/{toDay}")
    @JsonView(Views.QuestionThree.class)
    public ResponseEntity laborSumm(@PathVariable Long userId,
                                    @PathVariable String fromDay,
                                    @PathVariable String toDay) {
        TimeConverter convert = new TimeConverter();
        List<Task> temp = taskService.findLaborCosts(userId, convert.strToDate(fromDay), convert.strToDate(toDay));
        TimeTrack timeTrack = new TimeTrack();
        Long time = 0L;
        for (Task task : temp) {
            time += convert.minuts(task);
            timeTrack.setAllTime(time);
        }
        return ResponseEntity.ok(timeTrack);
    }

    /* Delete task function */

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long userId,
                       @PathVariable("taskId") Task task){
        taskService.delete(task);
    }

    /* Function for deleting all user tasks */

    @DeleteMapping
    public void deleteAll(@PathVariable Long userId){
        List<Task> removeList = taskService.findAll(userId);
        for(Task task : removeList) {
            taskService.delete(task);
        }
    }

}
