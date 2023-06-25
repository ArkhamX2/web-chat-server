package ru.arkham.webchat.controller.response;

import lombok.Data;

import java.util.List;

/**
 * Тело данных пользовательской роли.
 */
@Data
public class RoleData {

    /**
     * Уникальное название роли.
     */
    private String name;

    /**
     * Связанные тела данных пользователей.
     */
    private List<UserData> userDataList;
}
