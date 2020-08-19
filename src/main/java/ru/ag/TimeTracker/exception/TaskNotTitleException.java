package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskNotTitleException extends RuntimeException {

    public TaskNotTitleException(Long id) {
        super("In task [" + id + "] title is null.");
    }

    public TaskNotTitleException() {
        super("In create task does not title.");
    }
}
