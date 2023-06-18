package ru.arkham.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.model.Role;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.RoleRepository;
import ru.arkham.webchat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис работы с пользователями.
 * TODO: Возможно, необходимо создать интерфейс для автоматического связывания.
 */
@Service
public class UserServiceImplementation {

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
    public UserServiceImplementation(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
     */
    public User findByName(String name) {
        return userRepository.findByName(name);
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
            Role role = roleRepository.findByName(roleName);

            if (role == null) {
                role = createRole(roleName);
            }

            roles.add(role);
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
