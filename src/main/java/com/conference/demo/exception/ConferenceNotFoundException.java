package com.conference.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "conference not found")
public class ConferenceNotFoundException extends Exception {
    public ConferenceNotFoundException(String message) {
        super(message);
    }
}
