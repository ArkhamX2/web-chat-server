package ru.arkham.webchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.service.UserServiceImplementation;

/**
 * Контроллер безопасности.
 */
@Controller
public class SecurityController {

    /**
     * Сервис работы с пользователями.
     */
    private final UserServiceImplementation userService;

    /**
     * Конструктор.
     * TODO: Нужна ли здесь аннотация @Autowired?
     * @param userService сервис работы с пользователями.
     */
    @Autowired
    public SecurityController(UserServiceImplementation userService) {
        this.userService = userService;
    }

    /*
     * GET запрос для авторизации пользователя.
     * @return название вида для отображения.
     *
    @GetMapping("/login")
    public String showLoginForm() {
        // TODO: Реализовать.
        return "login";
    }*/

    /**
     * GET запрос для регистрации пользователя.
     * @return название вида для отображения.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        User user = new User();

        model.addAttribute("user", user);

        // TODO: Реализовать.
        return "register";
    }

    /**
     * POST запрос для верификации регистрации пользователя.
     * @return название вида для отображения.
     */
    @PostMapping("/register/save")
    public String processRegistration(@ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByName(user.getName());

        if (existing != null) {
            // TODO: Какой номер ошибки?
            result.rejectValue(
                    "name",
                    "1",
                    "Аккаунт с указанным именем уже зарегистрирован!");
        }

        // Повторная регистрация при ошибке.
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);

        // TODO: Куда редиректить и каким образом?
        return "redirect:/register?success";
    }
}
