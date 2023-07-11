package ru.arkham.webchat.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, обозначающее ошибку поиска информации о чате.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends RuntimeException {

    /**
     * Конструктор.
     * @param message сообщение.
     */
    public ChatNotFoundException(String message) {
        super(message);
    }
}
