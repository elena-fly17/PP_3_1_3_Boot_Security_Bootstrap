package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserServiceRepository;
import ru.kata.spring.boot_security.demo.repository.UserServiceRepositoryJPA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceRepository {
    // РАЗНЫЕ ВАРИАНТЫ - СРЕДИ НИХ ЕСТЬ И СРАБОТАВШИЕ, И НЕСРАБОТАВШИЕ
    // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // это возможное дополнение к строке выше
    /*private BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoder;
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/
    /*@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/



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
        // метод спринг-даты save сохранит в юзера все знач-я, введенные в форму
        // id добавится сам
        // поля name, surname, age, phone, email, username и password введем в форму
        // ЕЩЕ ОДИН ВАРИАНТ МЕТОДА
        //- Получите зашифрованный пароль.
        //- Обновите сущность пользователя
        //(в результате чего зашифрованный пароль будет зашифрован во второй раз).
        //- Перепишите только пароль пользователя с зашифрованным паролем, полученным на шаг е 1
        User timeUser = getUser(user.getId());
        if (timeUser == null) { // если юзера нет в БД
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
            userServiceRepositoryJPA.save(user);
        } else { // юзер есть в БД
            // ДОПОЛНЕНИЕ
            String passwordForm = user.getPassword();
            String passwordBD = timeUser.getPassword();
               if (passwordForm.equals(passwordBD)) {
                   user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
                   userServiceRepositoryJPA.save(user);
                   user.setPassword(passwordBD);
               } else {
                   user.setPassword(passwordEncoder.encode(user.getPassword()));
                   user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
                   userServiceRepositoryJPA.save(user);
               }
            }
//            *//*String passwordFromBD = timeUser.getPassword();
//            user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
//            userServiceRepositoryJPA.save(user);
//            user.setPassword(passwordFromBD);*//*
//        // НОВЫЙ ВАРИАНТ МЕТОДА
//        *//*User timeUser = getUser(user.getId());
//        String passwordForm;
//        String passwordDB;
//        if (timeUser != null) { // юзер есть в БД
//            passwordForm = user.getPassword();
//            passwordDB = timeUser.getPassword();
//            if (!passwordEncoder.matches(passwordForm, passwordDB)) {
//                user.setPassword(passwordEncoder.encode(passwordForm));
//                userServiceRepositoryJPA.save(user);
//            } else {
//                userServiceRepositoryJPA.save(user);
//            }
//        } else { // юзера нет в БД
//            passwordForm = user.getPassword();
//            user.setPassword(passwordEncoder.encode(passwordForm));
//            user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
//            userServiceRepositoryJPA.save(user);
//        }*//*
        // ДОПОЛНЕНИЕ - РАБОЧИЙ ВАРИАНТ
//        *//*User timeUser = getUser(user.getId());
//        if (timeUser != null) {
//            userServiceRepositoryJPA.save(user);
//        } else {
//            String passwordFromForm = user.getPassword();
//            String passwordFromDB = timeUser.getPassword();
//            if (!passwordEncoder.matches(passwordFromForm, passwordFromDB)) {
//                user.setPassword(passwordEncoder.encode(passwordFromForm));
//                user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
//                userServiceRepositoryJPA.save(user);
//            } else {
//                userServiceRepositoryJPA.save(user);
//            }
//        }*//*
//        // ПРЕЖНИЙ ВАРИАНТ МЕТОДА
//        *//*user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(Collections.singleton((new Role(1, "ROLE_USER"))));
//        userServiceRepositoryJPA.save(user);*//*
    }









    public User getUser(int id) {
        Optional<User> userOptional = userServiceRepositoryJPA.findById(id);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public void deleteUser(int id) {
        userServiceRepositoryJPA.deleteById(id);
    }








}
