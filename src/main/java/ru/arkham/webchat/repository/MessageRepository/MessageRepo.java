package ru.arkham.webchat.repository.MessageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Messanger.Message;
import ru.arkham.webchat.model.User;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message,String> {
        List<Message> findByChatId(String chatId);
        List<Message> findByChatIdAndUserID(String ChatId, String UserId);

}
