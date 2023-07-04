package ru.arkham.webchat.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, обозначающее ошибку поиска информации о сообщении.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {

    /**
     * Конструктор.
     * @param message сообщение.
     */
    public MessageNotFoundException(String message) {
        super(message);
    }
}
