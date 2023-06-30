package ru.arkham.webchat.model.Messanger;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Notification {
    @Id
    private String id;
    private String chatId;
}
