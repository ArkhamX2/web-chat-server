package ru.arkham.webchat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.arkham.webchat.controller.mapper.UserMapper;
import ru.arkham.webchat.controller.request.LoginRequest;
import ru.arkham.webchat.controller.request.RegisterRequest;
import ru.arkham.webchat.controller.response.AuthResponse;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.service.UserService;

/**
 * Контроллер модуля безопасности.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(SecurityController.URL_HOME)
public class SecurityController {

    public static final String URL_HOME = "/security";
    public static final String URL_LOGIN = "/login";
    public static final String URL_REGISTER = "/register";

    /**
     * Сервис работы с пользователями.
     */
    private final UserService userService;

    /**
     * POST запрос авторизации пользователя.
     * @param loginRequest тело запроса авторизации.
     * @return тело ответа авторизации.
     */
    @PostMapping(URL_LOGIN)
    public AuthResponse processLogin(@Valid @RequestBody LoginRequest loginRequest) {
        // TODO: Сгенерировать токен и авторизовать.
        return new AuthResponse("test");
    }

    /**
     * POST запрос регистрации пользователя.
     * TODO: Обработать исключения.
     * @param registerRequest тело запроса регистрации.
     * @return тело ответа авторизации.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(URL_REGISTER)
    public AuthResponse processRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
        String name = registerRequest.getName();

        if (userService.hasUserByName(name)) {
            // TODO: Заменить исключением.
            return new AuthResponse("test");
        }

        // TODO: Изменить тело запроса для списка ролей.
        User user = UserMapper.toUser(registerRequest);
        user = userService.prepareNewUser(user);

        userService.saveUser(user);

        // TODO: Сгенерировать токен и авторизовать.
        return new AuthResponse("test");
    }
}
