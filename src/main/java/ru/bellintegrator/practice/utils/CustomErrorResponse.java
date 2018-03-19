package ru.bellintegrator.practice.utils;

/**
 * Created on 11.03.2018.
 */
public class CustomErrorResponse {

    private String error;

    public CustomErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
