package ru.bellintegrator.practice.users.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.bellintegrator.practice.users.model.User;

/**
 * Created жук on 18.03.2018.
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
}
