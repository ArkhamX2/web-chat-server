package ru.arkham.webchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор отправителя.
     */
    @Column(nullable = false)
    private Long senderId;

    /**
     * Идентификатор получателя.
     */
    @Column(nullable = false)
    private Long recipientId;

    /**
     * Список сообщений.
     */
    @OneToMany(mappedBy="chat")
    private List<Message> messages;
}
