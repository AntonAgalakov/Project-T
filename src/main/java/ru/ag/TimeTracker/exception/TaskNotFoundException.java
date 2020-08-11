package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("The task does not exist");
    }

    public TaskNotFoundException(Long id) {
        super("The task (" + id + ") does not exist");
    }
}
