package ru.bellintegrator.practice.office.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.bellintegrator.practice.office.model.Office;

/**
 * Created on 15.03.2018.
 */
public interface OfficeRepository extends CrudRepository<Office, Long>, JpaSpecificationExecutor<Office> {
}
