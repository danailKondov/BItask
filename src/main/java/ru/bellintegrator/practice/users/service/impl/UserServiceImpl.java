package ru.bellintegrator.practice.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.exceptionhandler.exceptions.UserException;
import ru.bellintegrator.practice.office.dao.OfficeRepository;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.referencebook.dao.CountryRepository;
import ru.bellintegrator.practice.referencebook.dao.DocumentRepository;
import ru.bellintegrator.practice.referencebook.model.Country;
import ru.bellintegrator.practice.referencebook.model.Document;
import ru.bellintegrator.practice.users.dao.UserRepository;
import ru.bellintegrator.practice.users.model.User;
import ru.bellintegrator.practice.users.service.UserService;
import ru.bellintegrator.practice.users.view.UserDto;
import ru.bellintegrator.practice.users.view.UserListDto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 18.03.2018.
 */
@Service
@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private DocumentRepository documentRepository;
    private CountryRepository countryRepository;
    private OfficeRepository officeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           DocumentRepository documentRepository,
                           CountryRepository countryRepository,
                           OfficeRepository officeRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.countryRepository = countryRepository;
        this.officeRepository = officeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findOne(id);
        if (user != null) {
            return new UserDto(user);
        } else {
            throw new UserException("В базе данных нет сотрудника с подобным ID: " + id);
        }
    }

    @Override
    public void updateUser(UserDto user) {
        Long id = user.getId();
        if (id == null) throw new UserException("Невозможно провести обновление пользователя - отсутсвует его ID.");
        User userFromDB = userRepository.findOne(id);
        if (userFromDB == null) throw new UserException("Невозможно провести обновление - " +
                "пользователь с таким ID отсутсвует в базе данных.");

        transferDataFromDtoToEntity(user, userFromDB);
        // работает без userRepository.save(userFromDB) - очевидно, он в persistence context'e
    }

    private void transferDataFromDtoToEntity(UserDto user, User userFromOrToDB) {
        userFromOrToDB.setFirstName(user.getFirstName());
        userFromOrToDB.setMiddleName(user.getMiddleName());
        userFromOrToDB.setLastName(user.getSecondName());
        userFromOrToDB.setPosition(user.getPosition());
        userFromOrToDB.setPhone(user.getPhone());

        if (user.getDocName() != null) {
            Document document = documentRepository.findDocumentByDocName(user.getDocName());
            if (document == null) {
                throw new UserException("Невозможно провести обновление или сохранение пользователя - " +
                        "в базе данных отсутсвует документ с таким именем: " + user.getDocName());
            } else {
                userFromOrToDB.setDocument(document);
            }
        } else {
            userFromOrToDB.setDocument(null);
            // вопрос к бизнес-логике: если в обновленном (сохраняемом) варианте нет типа дока, его обнулили,
            // то стоит ли сохранять номер дока и его дату?
        }

        userFromOrToDB.setDocNumber(user.getDocNumber());
        userFromOrToDB.setDocDate(user.getDocDate());

        if (user.getCitizenshipCode() != null) { // предположим, что код важнее названия
            Country country = countryRepository.findOne(user.getCitizenshipCode());
            if (country == null) {
                throw new UserException("Невозможно провести обновление или сохранение пользователя - " +
                        "в базе данных отсутсвует страна с подобным кодом: " + user.getCitizenshipCode());
            } else if (country.getName().equals(user.getCitizenshipName())) {
                userFromOrToDB.setCountry(country);
            } else {
                throw new UserException("Невозможно провести обновление или сохранение пользователя - " +
                        "код страны (" + user.getCitizenshipCode() + ") " +
                        "не соответствует названию (" + user.getCitizenshipName() + "). " +
                        "Данному коду соответствует: " + country.getName());
            }
        } else {
            userFromOrToDB.setCountry(null); // при отстутсвии кода страны или названия = обнуляем Citizenship
        }

        userFromOrToDB.setIdentified(user.getIdentified());
    }

    @Override
    public void deleteUser(UserDto user) {
        if (user.getId() == null) throw new UserException("Невозможно удалить пользователя, " +
                "поскольку не был передан параметр ID");
        userRepository.delete(user.getId());
    }

    @Override
    public void saveUser(UserDto user) {
        if (user.getOfficeId() == null) throw new UserException("Невозможно сохранить служащего без ID его офиса");
        Office office = officeRepository.findOne(user.getOfficeId());
        if (office == null) throw new UserException("Невозможно сохранить служащего - " +
                "нет офиса с подобным ID: " + user.getOfficeId());
        User userToAdd = new User();
        userToAdd.setOffice(office);
        transferDataFromDtoToEntity(user, userToAdd);
        userRepository.save(userToAdd);
    }

    @Override
    public List<UserListDto> getAllUsersByCriterias(UserDto user) {
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (user.getOfficeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("office").get("id"), user.getOfficeId()));
                }
                if (user.getFirstName() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("firstName"), user.getFirstName()));
                }
                if (user.getSecondName() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("lastName"), user.getSecondName()));
                }
                if (user.getMiddleName() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("middleName"), user.getMiddleName()));
                }
                if (user.getPosition() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("position"), user.getPosition()));
                }
                if (user.getDocCode() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("document").get("code"), user.getDocCode()));
                }
                if (user.getCitizenshipCode() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("country").get("code"), user.getCitizenshipCode()));
                }
                if (predicates.isEmpty()) {
                    // вернуть всех юзеров
                    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
                    return query.select(root).getRestriction();
                } else {
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }
        };
        List<User> users = userRepository.findAll(spec);
        List<UserListDto> result = new ArrayList<>();
        for (User userToTransform : users) {
            result.add(new UserListDto(userToTransform));
        }
        return result;
    }
}
