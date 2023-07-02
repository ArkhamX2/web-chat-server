package ru.arkham.webchat.controller.MessageController;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arkham.webchat.model.Messanger.Chat;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.MessageRepository.ChatRepo;

import java.util.List;

@RequestMapping("chat")
public class ChatConroller {
    @Autowired private ChatRepo chatRepo;
    @PostMapping("create/{name}")
    public Chat create (@RequestBody List<User> users, @PathVariable String name){
        Chat chat = new Chat();
        chat.setChatName(name);
        chat.setUsers(users);
        return chatRepo.save(chat);

    }
    @DeleteMapping("delete/{id}")
    public void delete (@PathVariable ("id") Chat chat){
        chatRepo.delete((chat));
    }
    @PutMapping("update/{id}")
    public  Chat update (@RequestBody Chat chat,@PathVariable("id") Chat DbChat){
        BeanUtils.copyProperties(chat,DbChat,"id");
        return  chatRepo.save(DbChat);
    }
    @GetMapping("id")
    public Chat chatByID (@PathVariable ("id") Chat chat)
    {
        return chat;
    }
    @GetMapping("yourchats")
    public List<Chat>  chatByUser (@RequestBody User user){
        return chatRepo.findByUser(user);
    }


}
