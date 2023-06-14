package ru.arkham.webchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер безопасности.
 */
@Controller
public class SecurityController {

    /**
     * GET запрос для авторизации пользователя.
     * @return название вида для отображения.
     */
    @GetMapping("/login")
    public String login() {
        // TODO: Реализовать.
        return "login";
    }
}
