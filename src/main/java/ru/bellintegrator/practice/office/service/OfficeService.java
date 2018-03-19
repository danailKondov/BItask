package ru.bellintegrator.practice.office.service;

import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.view.OfficeDto;

import java.util.List;

/**
 * Created on 15.03.2018.
 */
public interface OfficeService {
    Office getOfficeById(long id);
    void updateOffice(Office office);
    void deleteOffice(OfficeDto office);
    void saveOffice(Office office);
    List<OfficeDto> getAllOfficesByCriterias(Long orgId, String name, String phone, Boolean active);
}
