package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

// репозиторий относится к классу UserServiceImpl и создан, чтобы в указанном классе
// проводить круд-операции с использованием методов спринг-дата
// к указанному классу относятся 2 репозитория - этот и UserServiceRepository
@Repository
public interface UserServiceRepositoryJPA extends JpaRepository<User, Integer> {


}
