package ru.arkham.webchat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.model.Chat;
import ru.arkham.webchat.repository.ChatRepository;

import java.util.Optional;

/**
 * Сервис работы с чатами.
 */
@RequiredArgsConstructor
@Service
public class ChatService {

    /**
     * Репозиторий данных чатов.
     */
    private final ChatRepository chatRepository;

    /**
     * Попытаться получить чат.
     * @param senderId идентификатор отправителя.
     * @param recipientId идентификатор получателя.
     * @param createIfNotExist создать при отсутствии.
     * @return чат или ничего.
     */
    public Optional<Chat> getChat(Long senderId, Long recipientId, boolean createIfNotExist) {
        Optional<Chat> chat = chatRepository.findBySenderIdOrSenderId(senderId, recipientId);

        if (chat.isPresent()) {
            return chat;
        }

        if (!createIfNotExist) {
            return Optional.empty();
        }

        Chat newChat = new Chat();

        newChat.setSenderId(senderId);
        newChat.setRecipientId(recipientId);

        return Optional.of(chatRepository.save(newChat));
    }
}
