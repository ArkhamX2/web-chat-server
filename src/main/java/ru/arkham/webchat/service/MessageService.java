package ru.arkham.webchat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.model.Chat;
import ru.arkham.webchat.model.Message;
import ru.arkham.webchat.model.MessageStatus;
import ru.arkham.webchat.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис работы с сообщениями.
 */
@RequiredArgsConstructor
@Service
public class MessageService {

    /**
     * Репозиторий данных сообщений.
     */
    private final MessageRepository messageRepository;

    /**
     * Сервис работы с чатами.
     */
    private final ChatService chatService;

    /**
     * Сохранить полученное сообщение.
     * @param message сообщение.
     * @return сохраненное сообщение.
     */
    public Message saveAndUpdateStatus(Message message) {
        message.setStatus(MessageStatus.RECEIVED);
        messageRepository.save(message);

        return message;
    }

    /**
     * Подсчитать количество полученных сообщений.
     * @param senderId идентификатор отправителя.
     * @param chatId идентификатор чата.
     * @return количество сообщений.
     */
    public Long countReceived(Long senderId, Long chatId) {
        return messageRepository.countBySenderIdAndChatIdAndStatus(
                senderId, chatId, MessageStatus.RECEIVED);
    }

    /**
     * Получить список сообщений между первым и вторым участниками.
     * @param firstId идентификатор первого участника.
     * @param secondId идентификатор второго участника.
     * @return список сообщений или пустой список.
     */
    public List<Message> findAll(Long firstId, Long secondId) {
        Optional<Chat> chat = chatService.getChat(firstId, secondId, false);

        if (chat.isEmpty()) {
            return new ArrayList<>();
        }

        List<Message> messages = chat.get().getMessages();

        if(messages.size() > 0) {
            updateStatuses(firstId, chat.get().getId(), MessageStatus.DELIVERED);
        }

        return messages;
    }

    /**
     * Попытаться получить сообщение по идентификатору и отметить его как полученное.
     * @param id идентификатор.
     * @return сообщение или ничего.
     */
    public Optional<Message> find(Long id) {
        return messageRepository.findById(id)
                .map(sample -> {
                    sample.setStatus(MessageStatus.DELIVERED);

                    return messageRepository.save(sample);
                });
    }

    /**
     * обновить статусы сообщений от определенного отправителя в определенном чате.
     * TODO: проверить.
     * @param senderId идентификатор отправителя.
     * @param chatId идентификатор чата.
     * @param status статус отправления.
     */
    public void updateStatuses(Long senderId, Long chatId, MessageStatus status) {
        // TODO: UPDATE message SET status = '?' WHERE senderId = '?' AND chatId = '?';
    }
}
