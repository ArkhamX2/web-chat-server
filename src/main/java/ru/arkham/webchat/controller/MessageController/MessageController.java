package ru.arkham.webchat.controller.MessageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arkham.webchat.model.Messanger.Chat;
import ru.arkham.webchat.model.Messanger.Message;
import ru.arkham.webchat.repository.MessageRepository.MessageRepo;

import java.util.List;

@RequestMapping("message")
public class MessageController {
    @Autowired
    private MessageRepo messageRepo;
    @GetMapping("FromChat/{id}")
    public List<Message> GetMessagesByChatId(@PathVariable String id){
        return messageRepo.findByChatId(id);
    }
    @PostMapping ("{id}")
    public void PostMessege(@RequestBody Message message,@PathVariable String ChatID){

    }
}
