package ru.arkham.webchat.controller.mapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.controller.request.LoginRequest;
import ru.arkham.webchat.controller.request.RegisterRequest;
import ru.arkham.webchat.controller.response.UserData;
import ru.arkham.webchat.model.Role;
import ru.arkham.webchat.model.User;

import java.util.List;

/**
 * Конвертер данных пользователя.
 */
@Service
public class UserMapper {

    /**
     * Получить пользователя из тела данных.
     * @param userData тело данных пользователя.
     * @return пользователь.
     */
    public static User toUser(@NotNull UserData userData) {
        User user = new User();

        user.setName(userData.getName());

        return user;
    }

    /**
     * Получить пользователя из тела запроса.
     * @param loginRequest тело запроса авторизации.
     * @return пользователь.
     */
    public static User toUser(@NotNull LoginRequest loginRequest) {
        User user = new User();

        user.setName(loginRequest.getName());
        user.setPassword(loginRequest.getPassword());

        return user;
    }

    /**
     * Получить пользователя из тела запроса.
     * @param registerRequest тело запроса регистрации.
     * @return пользователь.
     */
    public static User toUser(@NotNull RegisterRequest registerRequest) {
        Role role = new Role();
        User user = new User();

        role.setName(registerRequest.getRoleName());

        user.setName(registerRequest.getName());
        user.setPassword(registerRequest.getPassword());
        user.setRoles(List.of(role));

        return user;
    }

    /**
     * Получить тело данных пользователя из объекта.
     * @param user пользователь.
     * @return тело данных пользователя.
     */
    public static UserData toUserData(@NotNull User user) {
        UserData userData = new UserData();

        userData.setName(user.getName());

        return userData;
    }
}
