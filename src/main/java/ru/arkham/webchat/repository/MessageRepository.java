package ru.arkham.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Message;
import ru.arkham.webchat.model.MessageStatus;

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

    /**
     * Подсчитать количество сообщений от определенного отправителя
     * в определенном чате и с определенным статусом.
     * @param senderId идентификатор отправителя.
     * @param chatId идентификатор чата.
     * @param status статус отправки.
     * @return количество сообщений.
     */
    Long countBySenderIdAndChatIdAndStatus(Long senderId, Long chatId, MessageStatus status);

    /**
     * Обновить статусы всех сообщений от определенного отправителя
     * в определенном чате.
     * TODO: Протестировать.
     * @param senderId идентификатор отправителя.
     * @param chatId идентификатор чата.
     * @param status статус отправки.
     */
    void updateAllBySenderIdAndChatId(Long senderId, Long chatId, MessageStatus status);
}
