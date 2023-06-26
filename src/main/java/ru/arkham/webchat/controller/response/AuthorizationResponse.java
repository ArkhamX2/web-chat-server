package ru.arkham.webchat.controller.response;

import lombok.Data;

/**
 * Тело ответа авторизации.
 */
@Data
public class AuthorizationResponse {

    /**
     * Уникальный токен пользователя.
     */
    private final String token;
}
