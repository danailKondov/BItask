package ru.bellintegrator.practice.registration.exceptions;

/**
 * Created on 08.03.2018.
 */
public class AccountException extends RuntimeException {

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(String message) {
        super(message);
    }
}
