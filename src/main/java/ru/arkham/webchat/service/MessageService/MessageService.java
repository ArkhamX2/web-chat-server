package ru.arkham.webchat.service.MessageService;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import ru.arkham.webchat.model.Messanger.Message;
import ru.arkham.webchat.model.Messanger.MessageStatus;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.MessageRepository.MessageRepo;

import java.util.List;

public class MessageService {
    @Autowired
    MessageRepo messageRepo;
    private void updateStatus(MessageStatus status, User user, String ChatId){
        List<Message> messageList= messageRepo.findByChatIdAndUserID( ChatId,user.getId().toString());
        for (Message message:messageList ) {
            message.setStatus(status);
            messageRepo.save(message);
        }
    }
}
