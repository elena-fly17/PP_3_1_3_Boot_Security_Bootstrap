package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;

// репозиторий также создан для класса UserServiceImpl просто как обыч. репозиторий сервис. слоя
// к указанному классу относятся 2 репозитория - этот и UserServiceRepositoryJPA
@Repository
public interface UserServiceRepository {
    List<User> getUsers();
    void saveUser(User user);
    User getUser(int id);
    void deleteUser(int id);
}
