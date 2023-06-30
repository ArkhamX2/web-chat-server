package ru.arkham.webchat.repository.MessageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Messanger.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message,String> {
        Optional<List<Message>> findByChatId();
}
