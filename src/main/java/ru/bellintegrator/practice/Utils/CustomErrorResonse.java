package ru.bellintegrator.practice.Utils;

/**
 * Created on 11.03.2018.
 */
public class CustomErrorResonse {

    private String error;

    public CustomErrorResonse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
