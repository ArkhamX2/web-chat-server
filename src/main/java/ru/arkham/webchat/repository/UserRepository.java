package ru.arkham.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.User;

/**
 * Репозиторий данных пользователей.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Найти пользователя по его имени.
     * @param username Логин.
     * @return Пользователь.
     */
    User findByUsername(String username);
}
