package ru.arkham.webchat.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело запроса выхода.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Флаг удаления.
     * Обозначает необходимость удаления пользователя из базы данных.
     */
    private Boolean isForRemoval;
}
