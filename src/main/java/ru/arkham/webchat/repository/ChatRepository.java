package ru.arkham.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Chat;

import java.util.Optional;

/**
 * Репозиторий данных чатов.
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Найти чат по идентификаторам отправителя или получателя.
     * @param firstId идентификатор первого участника.
     * @param secondId идентификатор второго участника.
     * @return чат или ничего.
     */
    Optional<Chat> findByFirstEndpointIdOrSecondEndpointId(Long firstId, Long secondId);
}
