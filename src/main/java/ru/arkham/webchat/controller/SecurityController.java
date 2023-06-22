package ru.arkham.webchat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arkham.webchat.controller.request.RegisterRequest;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер модуля безопасности.
 * TODO: Переделать все URL в константы.
 * TODO: Переделать все тела ответов в константы или использовать отдельные классы.
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    /**
     * Сервис работы с пользователями.
     */
    private final UserService userService;

    /**
     * Конструктор.
     * @param userService сервис работы с пользователями.
     */
    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET запрос регистрации пользователя.
     * @return тело ответа.
     */
    @GetMapping("/register")
    public ResponseEntity<String> processRegistration() {
        // TODO: Передавать RegisterRequest?

        return ResponseEntity.ok("GET_REGISTER_OK");
    }

    /**
     * GET запрос авторизации пользователя.
     * @return тело ответа.
     */
    @GetMapping("/login")
    public ResponseEntity<String> processLogin() {
        // TODO: Передавать LoginRequest?

        return ResponseEntity.ok("GET_LOGIN_OK");
    }

    /**
     * POST запрос регистрации пользователя.
     * TODO: Обработать исключения.
     * @param registerRequest тело запроса.
     * @return тело ответа.
     */
    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
        String name = registerRequest.getName();

        if (userService.findUserByName(name) != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("POST_REGISTER_USER_EXISTS");
        }

        User user = userService.mapUser(registerRequest);

        userService.saveUser(user);

        return ResponseEntity.ok("POST_REGISTER_OK");
    }
}
