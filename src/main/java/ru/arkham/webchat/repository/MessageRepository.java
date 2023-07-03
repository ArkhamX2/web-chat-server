package ru.arkham.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Chat;
import ru.arkham.webchat.model.Message;

import java.util.Optional;

/**
 * Репозиторий данных сообщений.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Найти сообщение по идентификатору чата.
     * @param chatId идентификатор чата.
     * @return сообщение или ничего.
     */
    Optional<Message> findByChatId(Long chatId);
}
