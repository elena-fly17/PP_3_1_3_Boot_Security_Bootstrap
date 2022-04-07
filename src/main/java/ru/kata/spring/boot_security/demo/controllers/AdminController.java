package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

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

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userServiceImpl.getUsers());
        return "list_of_users";
    }

    @GetMapping("/showForm")
    public ModelAndView showFormforAdd() {
        User user = new User();
        ModelAndView mav = new ModelAndView("form_for_user");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin/list";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") Integer id) {
        User user = userServiceImpl.getUser(id);
        ModelAndView mav = new ModelAndView("form_for_user");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable (value = "id") int id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/list";
    }

    @GetMapping("/showUserInfo/{id}")
    public String showUserPage(@PathVariable (value = "id") int id, Model model) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "specific_user";
    }
}
