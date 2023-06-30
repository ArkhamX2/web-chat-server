package ru.arkham.webchat.repository.MessageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkham.webchat.model.Messanger.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat,String> {
        Optional<List<Chat>> GetAllChat();

}
