package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskAlreadyFinishedException extends RuntimeException {

    public TaskAlreadyFinishedException(Long id) {
        super("Task [" + id + "] finished already.");
    }
}
