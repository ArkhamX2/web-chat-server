package ru.arkham.webchat.controller.payload;

import ru.arkham.webchat.controller.payload.request.MessageRequest;
import ru.arkham.webchat.model.Message;

/**
 * Конвертер данных сообщения.
 */
public abstract class MessageMapper {

    /**
     * Получить сообщение из тела данных.
     * @param request тело данных сообщения.
     * @return сообщение (неполное).
     */
    public static Message toMessage(MessageRequest request) {
        Message message = new Message();

        message.setSenderId(request.getSenderId());
        message.setContent(request.getContent());

        return message;
    }

    /**
     * Получить тело данных из сообщения.
     * @param message сообщение.
     * @return тело запроса сообщения.
     */
    public static MessageRequest toRequest(Message message) {
        MessageRequest request = new MessageRequest();

        request.setSenderId(message.getSenderId());
        request.setContent(message.getContent());

        return request;
    }
}
