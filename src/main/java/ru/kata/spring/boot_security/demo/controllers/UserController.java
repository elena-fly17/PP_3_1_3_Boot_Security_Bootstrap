package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

// еще один вариант аннотирования данного класса:
/*@RestController
@RequestMapping("/user")*/

// @ResponseBody - не понимаю, зачем добавила эту аннотацию - она добавляется к методам,
// а не к классам - ну, а вдаваться в ее смысл сейчас вообще не хочу
@Controller
@RequestMapping("/user")
public class UserController {

    /*private UserServiceRepositoryJPA userServiceRepositoryJPA;
    @Autowired
    public void setUserServiceRepositoryJPA(UserServiceRepositoryJPA userServiceRepositoryJPA) {
        this.userServiceRepositoryJPA = userServiceRepositoryJPA;
    }*/

    // зависимость, чтобы получать нужного юзера из БД
    // выше вариант добавления др. зависимости для этого, но чтобы она работала,
    // нужно в ее и-се напис. метод findByUsername - а какой смысл это делать,
    // если этот метод уже прописан в репозитории UserRepository)
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*// СТАРЫЙ ВАРИАНТ МЕТОДА
    // возможно, во входящих параметрах метода нужен др. вид юзера - нет, потому что
    // когда юзер вводит данные, то это обыч. юзер, а не секьюрный
    // раньше был пост-маппинг на урл "/login" и аннотация метода @PostMapping
    // этот метод, чтобы отображать для юзера стр. с его данными
    @GetMapping("/user")
    public String showUserPage(@RequestBody User user, Model model) {
        // получили из тела пост-запроса юзернейм юзера
        String username = user.getUsername();
        // положили сюда юзера, полученного из БД по юзернейму
        model.addAttribute("user", userRepository.findByUsername(username));
        // Алишев при названии вьюшки указаывает и название папки, в которой она лежит
        return "specific_user";
    }*/

    // возле аннотации в скобках был урл "/user" - убрала, т.к. он указан как общий для класса
    @GetMapping("/{id}")
    public String showUserPage(Principal principal, Model model) {
        // когда юзер пройдет аутентификацию, данные о нем будут храниться в Principal -
        // из Principal получаем юзернейм юзера (метод getName возвращает юзернейм)
        // и по этому юзернейму находим юзера в БД
        // 2 строки ниже можно объединить в одну
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "specific_user";
    }
}
