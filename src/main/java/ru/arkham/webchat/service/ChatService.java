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
     * @param firstId идентификатор первого участника.
     * @param secondId идентификатор второго участника.
     * @param createIfNotExist создать при отсутствии.
     * @return чат или ничего.
     */
    public Optional<Chat> getChat(Long firstId, Long secondId, boolean createIfNotExist) {
        Optional<Chat> chat = chatRepository.findByFirstEndpointIdOrSecondEndpointId(firstId, secondId);

        if (chat.isPresent()) {
            return chat;
        }

        chat = chatRepository.findByFirstEndpointIdOrSecondEndpointId(secondId, firstId);

        if (chat.isPresent()) {
            return chat;
        }

        if (!createIfNotExist) {
            return Optional.empty();
        }

        Chat newChat = new Chat();

        newChat.setFirstEndpointId(firstId);
        newChat.setSecondEndpointId(secondId);

        return Optional.of(chatRepository.save(newChat));
    }
}
