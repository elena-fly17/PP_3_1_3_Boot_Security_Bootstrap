package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping()
    public String listUsers(Principal principal, Model model) {
        model.addAttribute("users", userServiceImpl.getUsers());
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("user_on_navbar",
                userRepository.findByUsername(principal.getName()));
        return "application_page";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles_arr", required = false) String[] roles) {
        List<String> rolesList = Arrays.asList(roles);
        if (rolesList.contains("ROLE_ADMIN")) {
            user.setRoles(Set.of(new Role(1, "ROLE_USER"),
                    new Role(2, "ROLE_ADMIN")));
        } else if (rolesList.contains("ROLE_USER")) {
            user.setRoles(Set.of(new Role(1, "ROLE_USER")));
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public String update(User user,
                         @RequestParam(value = "roles_arr") String[] roles) {
        List<String> rolesList = Arrays.asList(roles);
        if (rolesList.contains("ROLE_ADMIN")) {
            user.setRoles(Set.of(new Role(1, "ROLE_USER"),
                    new Role(2, "ROLE_ADMIN")));
        } else if ((rolesList.contains("ROLE_USER"))) {
            user.setRoles(Set.of(new Role(1, "ROLE_USER")));
        }
        userServiceImpl.updateUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public Optional<User> getUser(int id) {
        return userServiceImpl.getUser(id);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(int id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }
}
