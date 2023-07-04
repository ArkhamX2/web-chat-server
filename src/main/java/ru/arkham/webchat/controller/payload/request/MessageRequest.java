package ru.arkham.webchat.controller.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Запрос отправки сообщения.
 */
@Data
public class MessageRequest {

    /**
     * Идентификатор отправителя.
     */
    @NotBlank
    private Long senderId;

    /**
     * Идентификатор получателя.
     */
    @NotBlank
    private Long recipientId;

    /**
     * Наполнение.
     */
    @NotBlank
    private String content;
}
