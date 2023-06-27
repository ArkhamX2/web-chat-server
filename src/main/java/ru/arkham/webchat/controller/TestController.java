package ru.arkham.webchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkham.webchat.controller.mapper.UserMapper;
import ru.arkham.webchat.controller.response.UserData;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(TestController.URL_HOME)
public class TestController {

    public static final String URL_HOME = "/test";

    private final UserRepository userRepository;

    @GetMapping("/all")
    public List<UserData> processLogin() {
        List<User> userList = userRepository.findAll();
        List<UserData> userDataList = new ArrayList<>();

        for (User user: userList) {
            userDataList.add(UserMapper.toUserData(user));
        }

        return userDataList;
    }
}
