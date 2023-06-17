package ru.arkham.webchat.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело запроса авторизации.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Пароль пользователя.
     */
    private String password;
}
