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
    // ВОЗМОЖНО, ВО ВЬЮ НУЖНО ИЗМЕНИТЬ УРЛЫ - ДОПИСАТЬ ПРИСТВКУ АДМИН В НАЧАЛЕ

    // внедрение зависимости, как у гикбрейнс - через сеттер
    // сначала было через поле с @Autowired, идея предлагала через конструктор
    private UserServiceImpl userServiceImpl;
    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    // ДОБАВЛЕНО НОВОЕ СЕГОДНЯ
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

    // ВОТ ТАКИМ БЫЛ ЭТОТ МЕТОД В РАБОТАЮЩЕЙ ВЕРСИИ
    @GetMapping("/showForm")
    public String showFormForAdd(Model model) {
        model.addAttribute("user", new User());
        return "form_for_user";
    }
    /*// НОВАЯ ИЗМЕНЕННАЯ ВЕРСИЯ - МОЙ СОБСТВЕННЫЙ ВАРИАНТ - ПОТОМ ЕГО ОПРОБОВАТЬ
    @GetMapping("/showForm")
    public String showFormForAdd(Model model) {
        User user = new User();
        List<Role> roles = (List<Role>) roleRepository.findAll();
        user.setRoles(roles);
        model.addAttribute("user", user);
        return "form_for_user";
    }*/
    /*// ВАРИАНТ ПО ТУТОРИАЛУ
    @GetMapping("/showForm")
    public ModelAndView showFormForAdd() {
        User user = new User();
        ModelAndView mav = new ModelAndView("form_for_user");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }*/

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin/list";
    }

    /*@GetMapping("/updateForm/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        //return "form_for_user";
        return "form_for_update_user";
    }
    @GetMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return "redirect:/admin/list";
    }*/


    // НОВАЯ ИЗМЕНЕННАЯ ВЕРСИЯ - МОЙ СОБСТВЕННЫЙ ВАРИАНТ - ПОТОМ ЕГО ОПРОБОВАТЬ
    /*@GetMapping("/updateForm/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        User user = userServiceImpl.getUser(id);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        user.setRoles(roles);
        model.addAttribute("user", user);
        return "form_for_user";
    }*/
    /*// МЕТОД ПО ТУТОРИАЛУ
    @GetMapping("/updateForm/{id}")
    public ModelAndView showFormForUpdate(@PathVariable(name = "id") Integer id) {
        User user = userServiceImpl.getUser(id);
        ModelAndView mav = new ModelAndView("form_for_user");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }*/

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable (value = "id") int id, Model model) {
        User user = userServiceImpl.getUser(id);
        model.addAttribute("user", user);
        return "form_for_user";
    }





    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable (value = "id") int id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/list";
    }






    @GetMapping("/showUserInfo/{id}")
    public String showUserPage(@PathVariable (value = "id") int id, Model model) {
        // userServiceImpl.getUser(id);
        // return "redirect:/user/{id}";
        User user = userServiceImpl.getUser(id);
        model.addAttribute("user", user);
        return "specific_user";
    }
}
