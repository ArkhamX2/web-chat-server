package ru.arkham.webchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Сообщение.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

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
     * Чат.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Chat chat;

    /**
     * Наполнение.
     */
    private String content;

    /**
     * Дата создания.
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * Статус отправки.
     */
    private MessageStatus status;
}
