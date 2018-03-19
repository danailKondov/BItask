package ru.bellintegrator.practice.referencebook.dao;

import org.springframework.data.repository.CrudRepository;
import ru.bellintegrator.practice.referencebook.model.Country;

/**
 * Created on 18.03.2018.
 */
public interface CountryRepository extends CrudRepository<Country, Long>{
}
