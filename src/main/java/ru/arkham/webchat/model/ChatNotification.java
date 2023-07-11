package ru.arkham.webchat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Уведомление чата.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatNotification {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Идентификатор отправителя.
     */
    private Long senderId;
}
