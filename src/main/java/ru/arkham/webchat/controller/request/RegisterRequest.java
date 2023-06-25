package ru.arkham.webchat.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static ru.arkham.webchat.model.Role.NAME_DEFAULT;

/**
 * Тело запроса регистрации.
 */
@Data
public class RegisterRequest {

    /**
     * Имя пользователя.
     */
    @NotBlank
    private String name;

    /**
     * Пароль пользователя.
     */
    @NotBlank
    private String password;

    /**
     * Название роли пользователя.
     */
    @NotBlank
    private String roleName = NAME_DEFAULT;
}
