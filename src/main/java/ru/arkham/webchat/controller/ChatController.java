package ru.arkham.webchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.arkham.webchat.controller.exception.ChatNotFoundException;
import ru.arkham.webchat.controller.exception.MessageNotFoundException;
import ru.arkham.webchat.controller.payload.request.MessageRequest;
import ru.arkham.webchat.model.Chat;
import ru.arkham.webchat.model.ChatNotification;
import ru.arkham.webchat.model.Message;
import ru.arkham.webchat.service.ChatService;
import ru.arkham.webchat.service.MessageService;

import java.util.Optional;

/**
 * Контроллер чатов.
 * TODO: Поместить все пути в один файл настроек.
 */
@RequiredArgsConstructor
@Controller
public class ChatController {

    /**
     * Шаблон работы с сообщениями.
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Сервис работы с чатами.
     */
    private final ChatService chatService;

    /**
     * Сервис работы с сообщениями.
     */
    private final MessageService messageService;

    /**
     * Обработать новое сообщение.
     * @param request тело запроса сообщения.
     * @throws ChatNotFoundException при невозможности создания чата.
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload MessageRequest request) throws ChatNotFoundException {
        Chat chat = chatService
                .getChat(request.getSenderId(), request.getRecipientId(), true)
                .orElseThrow(() -> new ChatNotFoundException("Внутренняя ошибка создания чата!"));

        Message message = new Message();

        // TODO: Нужен мэппер.
        message.setSenderId(request.getSenderId());
        message.setChat(chat);
        message.setContent(request.getContent());

        message = messageService.saveAndUpdateStatus(message);

        messagingTemplate.convertAndSendToUser(
                request.getRecipientId().toString(),
                "/queue/messages",
                new ChatNotification(message.getId(), message.getSenderId()));
    }

    /**
     * GET запрос подсчета новых сообщений.
     * @param firstId идентификатор первого участника (отправителя).
     * @param secondId идентификатор второго участника.
     * @return количество новых сообщений.
     * @throws ChatNotFoundException при отсутствии чата между двумя участниками.
     */
    @GetMapping("/messages/{firstId}/{secondId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable Long firstId, @PathVariable Long secondId) throws ChatNotFoundException {
        Chat chat = chatService
                .getChat(firstId, secondId, true)
                .orElseThrow(() -> new ChatNotFoundException("Чат между указанными участниками не найден!"));

        return ResponseEntity
                .ok(messageService.countReceived(firstId, chat.getId()));
    }

    /**
     * GET запрос поиска списка сообщений между первым и вторым участниками.
     * @param firstId идентификатор первого участника.
     * @param secondId идентификатор второго участника.
     * @return список сообщений.
     */
    @GetMapping("/messages/{firstId}/{secondId}")
    public ResponseEntity<?> findChatMessages(@PathVariable Long firstId, @PathVariable Long secondId) {
        return ResponseEntity
                .ok(messageService.findAll(firstId, secondId));
    }

    /**
     * GET запрос поиска сообщения по его идентификатору.
     * @param id идентификатор.
     * @return сообщение.
     * @throws MessageNotFoundException при отсутствии сообщения.
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage(@PathVariable Long id) throws MessageNotFoundException {
        Optional<Message> message = messageService.find(id);

        if (message.isEmpty()) {
            throw new MessageNotFoundException("Сообщение не найдено!");
        }

        return ResponseEntity.ok(message);
    }
}
