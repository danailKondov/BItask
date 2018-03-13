package ru.bellintegrator.practice.registration.service;

/**
 * Created on 13.03.2018.
 */
public interface ComputeHashService {

    String getSHA256HashFromString(String source);
}
