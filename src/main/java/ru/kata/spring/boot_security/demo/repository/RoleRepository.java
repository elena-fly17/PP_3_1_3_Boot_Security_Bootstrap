package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
