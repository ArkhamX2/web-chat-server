package ru.arkham.webchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Пользовательская роль.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role
{
    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальное название.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Список связанных пользователей.
     */
    @ManyToMany(mappedBy="roles")
    private List<User> users = new ArrayList<>();
}
