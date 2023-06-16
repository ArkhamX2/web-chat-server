package ru.arkham.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Role;

/**
 * Репозиторий данных пользователей.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Найти пользовательскую роль по ее названию.
     * @param name название.
     * @return Пользовательская роль.
     */
    Role findByName(String name);
}
