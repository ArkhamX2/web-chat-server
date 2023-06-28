package ru.arkham.webchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkham.webchat.controller.mapper.UserMapper;
import ru.arkham.webchat.controller.response.UserData;
import ru.arkham.webchat.exception.UserNotFoundException;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер пользователей.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(UserController.URL_HOME)
public class UserController {

    public static final String URL_HOME = "/user";

    /**
     * Сервис работы с пользователями.
     */
    private final UserService userService;

    /**
     * GET запрос получения всех пользователей.
     * @return список пользователей.
     */
    @GetMapping
    public List<UserData> getAll() {
        List<User> userList = userService.findAll();
        List<UserData> userDataList = new ArrayList<>();

        for (User user: userList) {
            userDataList.add(UserMapper.toUserData(user));
        }

        return userDataList;
    }

    /**
     * GET запрос получения текущего пользователя.
     * @param userDetails данные пользователя.
     * @return пользователь.
     */
    @GetMapping("/me")
    public UserData getCurrent(@AuthenticationPrincipal UserDetails userDetails) {
        String name = userDetails.getUsername();
        User user = userService
                .findUserByName(name)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));

        return UserMapper.toUserData(user);
    }
}