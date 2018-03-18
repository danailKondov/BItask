package ru.bellintegrator.practice.exceptionhandler.exceptions;

/**
 * Created on 18.03.2018.
 */
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
