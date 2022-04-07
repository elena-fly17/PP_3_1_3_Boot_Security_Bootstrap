package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserServiceRepositoryJPA;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceRepositoryJPA userServiceRepositoryJPA;
    @Autowired
    public void setUserServiceRepositoryJPA(UserServiceRepositoryJPA userServiceRepositoryJPA) {
        this.userServiceRepositoryJPA = userServiceRepositoryJPA;
    }

    public List<User> getUsers() {
        return userServiceRepositoryJPA.findAll();
    }

    public void saveUser(User user) {
        User timeUser = getUser(user.getId());
        if (timeUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userServiceRepositoryJPA.save(user);
        } else {
            String passwordForm = user.getPassword();
            String passwordBD = timeUser.getPassword();
            if (passwordForm.equals(passwordBD)) {
                userServiceRepositoryJPA.save(user);
                user.setPassword(passwordBD);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userServiceRepositoryJPA.save(user);
            }
        }
    }

    public User getUser(int id) {
        Optional<User> userOptional = userServiceRepositoryJPA.findById(id);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public void deleteUser(int id) {
        userServiceRepositoryJPA.deleteById(id);
    }
}
