package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserServiceCrudRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserServiceCrudRepository userServiceCrudRepository;
    @Autowired
    public void setUserServiceCrudRepository(UserServiceCrudRepository userServiceCrudRepository) {
        this.userServiceCrudRepository = userServiceCrudRepository;
    }

    public List<User> getUsers() {
        return (List<User>) userServiceCrudRepository.findAll();
    }

    public void saveUser(User user) {
        Optional<User> timeUser = getUser(user.getId());
        if (timeUser.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUsername(user.getEmail());
            userServiceCrudRepository.save(user);
        } else {
            user.setUsername(user.getEmail());
            User user1 = timeUser.get();
            String passwordForm = user.getPassword();
            String passwordBD = user1.getPassword();
            if (passwordForm.equals(passwordBD)) {
                userServiceCrudRepository.save(user);
                user.setPassword(passwordBD);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userServiceCrudRepository.save(user);
            }
        }
    }

    public void updateUser(User user) {
        saveUser(user);
    }

    public Optional<User> getUser(int id) {
        return userServiceCrudRepository.findById(id);
    }

    public void deleteUser(int id) {
        userServiceCrudRepository.deleteById(id);
    }
}
