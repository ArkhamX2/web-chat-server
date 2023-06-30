package ru.arkham.webchat.model.Messanger;

import jakarta.persistence.Id;
import lombok.Data;
import ru.arkham.webchat.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Message {
    @Id
    private int id;
    private String chatId;
    private User ChatUser;
    private String content;
    private LocalDateTime timestamp;
    private MessageStatus status;
}
