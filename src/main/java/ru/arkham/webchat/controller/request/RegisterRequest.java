package ru.arkham.webchat.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело запроса регистрации.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * Имя пользователя.
     */
    @NotEmpty
    private String name;

    /**
     * Пароль пользователя.
     */
    @NotEmpty
    private String password;

    /**
     * Флаг администратора.
     * Обозначает необходимость назначения пользователю роли идминистратора.
     */
    private Boolean administratorFlag = false;
}
