package ru.ag.TimeTracker;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.service.TaskService;

import java.util.Date;
import java.util.List;

@Component
public class ClearDateBase {

    private final TaskService taskService;
    private static final long millisecondsOfDay = 86400000L;
    private static final long periodSave = 5184000000L; // Time when the completed task is stored in the database
                                                            // Storage time 2 months

    private static final Logger log = Logger.getLogger(ClearDateBase.class);

    @Autowired
    public ClearDateBase(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(fixedRate = millisecondsOfDay)
    public void clear() {
        Date nowDate = new Date();

        List<Task> taskList= taskService.findAll();

        for (Task task : taskList) {

            if(task.getDateEnd() != null) {
                if (nowDate.getTime() - task.getDateEnd().getTime() > periodSave) {
                    taskService.delete(task);
                }
            }
        }

        log.info("Old data in the database was purged");
    }
}
