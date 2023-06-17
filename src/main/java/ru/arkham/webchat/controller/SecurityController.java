package ru.arkham.webchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arkham.webchat.controller.request.LoginRequest;
import ru.arkham.webchat.controller.request.LogoutRequest;
import ru.arkham.webchat.controller.request.RegisterRequest;
import ru.arkham.webchat.service.UserServiceImplementation;

/**
 * Контроллер безопасности.
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    /**
     * Сервис работы с пользователями.
     */
    private final UserServiceImplementation userService;

    /**
     * Конструктор.
     * @param userService сервис работы с пользователями.
     */
    @Autowired
    public SecurityController(UserServiceImplementation userService) {
        this.userService = userService;
    }

    /**
     * GET запрос авторизации пользователя.
     * @return тело ответа.
     */
    @GetMapping("/login")
    public ResponseEntity<String> processLogin() {
        // TODO: Обработка логики входа пользователя.

        return ResponseEntity.ok("GET_LOGIN_OK");
    }

    /**
     * POST запрос авторизации пользователя.
     * @param loginRequest тело запроса.
     * @return тело ответа.
     */
    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@RequestBody LoginRequest loginRequest) {
        // TODO: Обработка логики входа пользователя.

        return ResponseEntity.ok("POST_LOGIN_OK");
    }

    /**
     * GET запрос регистрации пользователя.
     * @return тело ответа.
     */
    @GetMapping("/register")
    public ResponseEntity<String> processRegistration() {
        // TODO: Обработка логики регистрации пользователя.

        return ResponseEntity.ok("GET_REGISTER_OK");
    }

    /**
     * POST запрос регистрации пользователя.
     * @param registerRequest тело запроса.
     * @return тело ответа.
     */
    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@RequestBody RegisterRequest registerRequest) {
        // TODO: Обработка логики регистрации пользователя.

        return ResponseEntity.ok("POST_REGISTER_OK");
    }

    /**
     * GET запрос выхода пользователя.
     * @return тело ответа.
     */
    @GetMapping("/logout")
    public ResponseEntity<String> processLogout() {
        // TODO: Обработка логики выхода пользователя.

        return ResponseEntity.ok("GET_LOGOUT_OK");
    }

    /**
     * POST запрос выхода пользователя.
     * @param logoutRequest тело запроса.
     * @return тело ответа.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> processLogout(@RequestBody LogoutRequest logoutRequest) {
        // TODO: Обработка логики выхода пользователя.

        return ResponseEntity.ok("POST_LOGOUT_OK");
    }
}
