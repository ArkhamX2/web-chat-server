package ru.arkham.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.controller.request.RegisterRequest;
import ru.arkham.webchat.model.Role;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.RoleRepository;
import ru.arkham.webchat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.arkham.webchat.model.Role.NAME_DEFAULT;

/**
 * Сервис работы с пользователями.
 */
@Service
public class UserService {

    /**
     * Репозиторий пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий пользовательских ролей.
     */
    private final RoleRepository roleRepository;

    /**
     * Шифратор паролей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор.
     * @param userRepository репозиторий пользователей.
     * @param roleRepository репозиторий пользовательских ролей.
     * @param passwordEncoder шифратор паролей.
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Создать пользователя из тела запроса регистрации.
     * @param registerRequest тело запроса регистрации.
     * @return пользователь.
     */
    public User mapUser(RegisterRequest registerRequest) {
        User user = new User();
        String password = encodePassword(registerRequest.getPassword());
        List<String> roles = new ArrayList<>();

        // TODO: Изменить тело запроса для списка ролей.
        roles.add(NAME_DEFAULT);

        if (!registerRequest.getRoleName().equals(NAME_DEFAULT)) {
            roles.add(registerRequest.getRoleName());
        }

        user.setName(registerRequest.getName());
        user.setPassword(password);
        user.setRoles(createRoles(roles));

        return user;
    }

    /**
     * Сохранить пользователя.
     * @param user пользователь.
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Получить пользователя по имени.
     * @param name имя.
     * @return пользователь.
     * @throws NoSuchElementException при отсутствии пользователя.
     */
    public User findUserByName(String name) throws NoSuchElementException {
        return userRepository.findByName(name).orElseThrow();
    }

    /**
     * Получить хеш пароля.
     * @param rawPassword пароль.
     * @return хеш пароля.
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Создать и получить пользовательские роли.
     * @param roleNames названия ролей.
     * @return пользовательские роли.
     */
    public List<Role> createRoles(List<String> roleNames) {
        List<Role> roles = new ArrayList<>();

        for (String roleName: roleNames) {
            Optional<Role> role = roleRepository.findByName(roleName);

            if (role.isEmpty()) {
                roles.add(createRole(roleName));
                continue;
            }

            roles.add(role.get());
        }

        return roles;
    }

    /**
     * Создать и получить пользовательскую роль.
     * @param name название роли.
     * @return пользовательская роль.
     */
    private Role createRole(String name) {
        Role role = new Role();

        role.setName(name);

        return roleRepository.save(role);
    }
}
