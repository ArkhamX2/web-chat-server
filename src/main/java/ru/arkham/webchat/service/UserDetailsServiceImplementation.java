package ru.arkham.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.arkham.webchat.model.Role;
import ru.arkham.webchat.model.User;
import ru.arkham.webchat.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Сервис работы с данными пользователей.
 * Требуется для корректного функционирования модуля безопасности.
 */
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    /**
     * Репозиторий пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Конструктор.
     * @param userRepository репозиторий пользователей.
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
        User user = userRepository.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Получить разрешения на основе пользовательских ролей.
     * @param roles пользовательские роли.
     * @return разрешения.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
