package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskNotEndDateException extends RuntimeException {

    public TaskNotEndDateException(Long id) {
        super("Task [" + id + "] does not finish date!");
    }
}
