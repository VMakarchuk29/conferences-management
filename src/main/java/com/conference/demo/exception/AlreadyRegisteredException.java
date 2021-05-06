package com.conference.demo.exception;

public class AlreadyRegisteredException extends Exception {
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
