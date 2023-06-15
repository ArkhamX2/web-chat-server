package ru.arkham.webchat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигуратор бинов для системы безопасности.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    /**
     * Сервис загрузки данных пользователей.
     * TODO: Понять, что с этим делать.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Конструктор.
     * @param userDetailsService сервис загрузки данных пользователей.
     */
    @Autowired
    public SecurityConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Сервис запроса авторизации.
     * @param authenticationConfiguration конфигурация авторизации.
     * @return сервис запроса авторизации.
     * @throws Exception при внутренней ошибке.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // AuthenticationManagerBuilder auth
        // .userDetailsService(userDetailsService)
        // .passwordEncoder(passwordEncoder());

        // TODO: Переделать. Задействовать UserDetailsService.
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Фильтр доступа.
     * @param http строитель настроек безопасности.
     * @return фильтр доступа.
     * @throws Exception при внутренней ошибке.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        // TODO: Добавить регистрацию.
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated());
        // TODO: Доработать контроллер безопасности для дополнительных адресов.
        http.formLogin(configurer -> configurer
                //.usernameParameter("username")
                //.passwordParameter("password")
                //.failureUrl("/login?failed")
                //.loginProcessingUrl("/login/process")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll());
        http.logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    /**
     * Сервис шифрования.
     * @return сервис шифрования.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
