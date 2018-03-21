package ru.bellintegrator.practice.referencebook.service;

import ru.bellintegrator.practice.referencebook.model.Country;
import ru.bellintegrator.practice.referencebook.model.Document;

import java.util.List;

/**
 * Created on 21.03.2018.
 */
public interface ReferenceBookService {
    List<Document> getAllDocs();
    List<Country> getAllCountries();
}
