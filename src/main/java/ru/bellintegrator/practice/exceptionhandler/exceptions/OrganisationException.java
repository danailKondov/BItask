package ru.bellintegrator.practice.exceptionhandler.exceptions;

/**
 * Created on 11.03.2018.
 */
public class OrganisationException extends RuntimeException {

    public OrganisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrganisationException(String message) {
        super(message);
    }
}
