package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository; // идея предлагает поле сделать финальным

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional // гикбрейнс доб. аннотацию, чтобы исправить ленив. загрузку
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' is not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }

    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }

        // Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        Collection<Role> collection = new ArrayList<>();

        String[] authStrings = user.getTimeRoles().split(", ");

        for(String authString : authStrings) {
            //authorities.add(new SimpleGrantedAuthority(authString));
            if(authString.equalsIgnoreCase("ROLE_USER")) {

            }
            collection.add(new Role(authString));

            // user.setRoles(new Role(authString));
        }

        user.setRoles(collection);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getAuthorities());

        *//*UserDetails ud = new User(user.getUsername(), user.getPassword(), authorities);
        return ud;*//*
    }*/
}
