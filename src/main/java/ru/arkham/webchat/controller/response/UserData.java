package ru.arkham.webchat.controller.response;

import lombok.Data;

import java.util.List;

/**
 * Тело данных пользователя.
 */
@Data
public class UserData {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * Уникальное имя пользователя.
     */
    private String name;

    /**
     * Связанные тела данных пользовательских ролей.
     */
    private List<RoleData> roleDataList;
}
