package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

// для доступа к информации о роли из БД
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
