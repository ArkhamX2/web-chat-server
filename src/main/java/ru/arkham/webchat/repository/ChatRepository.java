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
     * @param senderId идентификатор отправителя.
     * @param recipientId идентификатор получателя.
     * @return чат или ничего.
     */
    Optional<Chat> findBySenderIdOrSenderId(Long senderId, Long recipientId);
}
