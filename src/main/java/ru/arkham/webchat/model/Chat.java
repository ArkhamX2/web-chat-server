package ru.arkham.webchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Чат между двумя участниками.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор первого участника.
     */
    @Column(nullable = false)
    private Long firstEndpointId;

    /**
     * Идентификатор второго участника.
     */
    @Column(nullable = false)
    private Long secondEndpointId;

    /**
     * Список сообщений.
     */
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
}
