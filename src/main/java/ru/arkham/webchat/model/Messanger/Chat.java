package ru.arkham.webchat.model.Messanger;

import jakarta.persistence.Id;
import lombok.Data;
import ru.arkham.webchat.model.User;

import java.util.List;
@Data
public class Chat {
    @Id
    private String id;
    private String chatName;
    private List<User> users;

}
