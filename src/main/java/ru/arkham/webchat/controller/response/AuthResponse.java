package ru.arkham.webchat.controller.response;

import lombok.Data;

/**
 * Тело ответа авторизации.
 */
@Data
public class AuthResponse {

    /**
     * Уникальный токен пользователя.
     */
    private final String token;
}
