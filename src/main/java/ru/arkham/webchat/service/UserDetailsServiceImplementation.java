package ru.arkham.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.UserRepository;

import java.util.List;

/**
 * Сервис загрузки данных пользователей.
 */
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    /**
     * Репозиторий данных пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Конструктор.
     * @param userRepository репозиторий данных пользователей.
     */
    @Autowired
    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получить данные пользователя по его имени.
     * @param username имя пользователя.
     * @return данные пользователя.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        // TODO: Добавить больше ролей.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER")));
    }
}
