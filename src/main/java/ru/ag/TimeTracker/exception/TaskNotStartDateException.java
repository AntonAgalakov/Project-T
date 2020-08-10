package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskNotStartDateException extends RuntimeException {

    public TaskNotStartDateException(Long id) {
        super("Task [" + id + "] will not finish, because it is not start date!");
    }
}
