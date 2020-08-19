package ru.ag.TimeTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AreaNotFoundException extends RuntimeException {

    public AreaNotFoundException(Long id) {
        super("The area (" + id + ") does not exist");
    }

    public AreaNotFoundException(String message) {
        super(message);
    }
}
