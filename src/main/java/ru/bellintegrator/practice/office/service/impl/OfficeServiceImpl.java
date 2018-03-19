package ru.bellintegrator.practice.office.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.OfficeException;
import ru.bellintegrator.practice.office.dao.OfficeRepository;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.OfficeDto;
import ru.bellintegrator.practice.orgs.dao.OrganisationRepository;
import ru.bellintegrator.practice.orgs.model.Organisation;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15.03.2018.
 */
@Service
@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OfficeServiceImpl implements OfficeService {

    private OfficeRepository officeRepository;

    private OrganisationRepository organisationRepository;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository, OrganisationRepository organisationRepository) {
        this.officeRepository = officeRepository;
        this.organisationRepository = organisationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Office getOfficeById(long id) {
        Office office = officeRepository.findOne(id);
        if (office != null) {
            return office;
        } else {
            throw new OfficeException("В базе нет офиса с подобным ID: " + id);
        }
    }

    /**
     * Метод обновляет значения офиса в базе данных.
     * @param sourceOffice с обновленными значениями
     */
    @Override
    public void updateOffice(Office sourceOffice) {
        if(sourceOffice.getId() == null) throw new OfficeException("Невозможно обновить офис, поскольку не был передан параметр ID");
        Office officeToUpdate = officeRepository.findOne(sourceOffice.getId());
        if (officeToUpdate != null) {
            // переписываем значения в объект, чтобы сохранить значения других полей, отсутствующих во входящем office
            officeToUpdate.setName(sourceOffice.getName());
            officeToUpdate.setAddress(sourceOffice.getAddress());
            officeToUpdate.setPhone(sourceOffice.getPhone());
            officeToUpdate.setActive(sourceOffice.isActive());
            officeRepository.save(officeToUpdate);
        } else {
            throw new OfficeException("Невозможно обновить, поскольку в базе нет офиса с подобным ID: " + sourceOffice.getId());
        }
    }

    @Override
    public void deleteOffice(OfficeDto office) {
        if(office.getId() == null) throw new OfficeException("Невозможно удалить офис, поскольку не был передан параметр ID");
        officeRepository.delete(office.getId());
    }

    @Override
    public void saveOffice(Office office) {
        Long orgId = office.getOrgId(); // откуда он приходит?
        if (orgId == null) throw new OfficeException("Невозможно сохранить офис без ID организации");
        Organisation organisation = organisationRepository.findOne(orgId);
        if (organisation != null) {
            office.setOrganisation(organisation);
            officeRepository.save(office);
        } else {
            throw new OfficeException("Невозможно сохранить офис: организация с подобным ID отсутствует в базе данных: " + orgId);
        }
    }

    @Override
    public List<OfficeDto> getAllOfficesByCriterias(Long orgId, String name, String phone, Boolean active) {
        Specification<Office> spec = new Specification<Office>() {
            @Override
            public Predicate toPredicate(Root<Office> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (orgId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("organisation").get("id"), orgId));
                }
                if (name != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                if (phone != null) {
                    predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
                }
                if (active != null) {
                    predicates.add(criteriaBuilder.equal(root.get("isActive"), active));
                }
                if (predicates.isEmpty()) {
                    // вернуть все офисы
                    CriteriaQuery<Office> query = criteriaBuilder.createQuery(Office.class);
                    return query.select(root).getRestriction();
                } else {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }
        };
        List<Office> offices = officeRepository.findAll(spec);
        List<OfficeDto> result = new ArrayList<>();
        for (Office office : offices) {
            result.add(new OfficeDto(office.getId(), office.getName(), office.isActive()));
        }
        return result;
    }
}
