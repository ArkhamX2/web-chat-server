package ru.arkham.webchat.repository.MessageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arkham.webchat.model.Messanger.Chat;
import ru.arkham.webchat.model.User;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat,String> {
        List<Chat> findByUser(User user);
}
