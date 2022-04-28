package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

@Repository
public interface UserServiceCrudRepository extends CrudRepository<User, Integer> {

}
