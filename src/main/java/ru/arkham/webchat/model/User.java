package ru.arkham.webchat.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Пользователь.
 */
@Data
@Entity
public class User {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Уникальное имя.
     */
    @Column(length = 64, nullable = false, unique = true)
    private String username;

    /**
     * Пароль.
     */
    @Column(length = 256, nullable = false)
    private String password;
}
