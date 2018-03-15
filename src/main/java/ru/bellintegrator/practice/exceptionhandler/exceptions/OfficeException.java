package ru.bellintegrator.practice.exceptionhandler.exceptions;

/**
 * Created on 15.03.2018.
 */
public class OfficeException extends RuntimeException {

    public OfficeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfficeException(String message) {
        super(message);
    }
}
