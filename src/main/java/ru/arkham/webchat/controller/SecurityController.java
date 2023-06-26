package ru.arkham.webchat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arkham.webchat.controller.mapper.UserMapper;
import ru.arkham.webchat.controller.request.RegisterRequest;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.service.UserService;

import java.util.NoSuchElementException;

/**
 * Контроллер модуля безопасности.
 * TODO: Переделать все тела ответов в константы или использовать отдельные классы.
 */
@RestController
@RequestMapping(SecurityController.URL_HOME)
public class SecurityController {

    public static final String URL_HOME = "/security";
    public static final String URL_LOGIN = "/login";
    public static final String URL_REGISTER = "/register";
    public static final String URL_HOME_LOGIN = URL_HOME + URL_LOGIN;
    public static final String URL_HOME_REGISTER = URL_HOME + URL_REGISTER;

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
    @GetMapping(URL_REGISTER)
    public ResponseEntity<String> processRegistration() {
        // TODO: Передавать RegisterRequest?

        return ResponseEntity.ok("GET_REGISTER_OK");
    }

    /**
     * GET запрос авторизации пользователя.
     * @return тело ответа.
     */
    @GetMapping(URL_LOGIN)
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
    @PostMapping(URL_REGISTER)
    public ResponseEntity<String> processRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
        String name = registerRequest.getName();

        try {
            userService.findUserByName(name);
        } catch (NoSuchElementException exception) {
            // TODO: Изменить тело запроса для списка ролей.
            User user = UserMapper.toUser(registerRequest);
            user = userService.prepareNewUser(user);

            userService.saveUser(user);

            return ResponseEntity.ok("POST_REGISTER_OK");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("POST_REGISTER_USER_EXISTS");
    }
}
