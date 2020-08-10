package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskNotDescriptionException extends RuntimeException {

    public TaskNotDescriptionException(Long id) {
        super("In task [" + id + "] description is null.");
    }

    public TaskNotDescriptionException() {
        super("In create task does not description.");
    }
}
