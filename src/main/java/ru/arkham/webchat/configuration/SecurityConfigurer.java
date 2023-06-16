package ru.arkham.webchat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    /*
     * TODO: Вызывает рекурсивное связывание. Найти альтернативу.
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }*/

    /**
     * Менеджер авторизации.
     * @param http строитель настроек безопасности.
     * @return менеджер авторизации.
     * @throws Exception при внутренней ошибке.
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        return builder.build();
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
        // TODO: Реализовать регистрацию.
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/register/**").permitAll()
                .anyRequest().authenticated());
        // TODO: Доработать контроллер безопасности для дополнительных адресов.
        http.formLogin(configurer -> configurer
                //.loginPage("/login") // GET на этот URL для формы входа.
                //.loginProcessingUrl("/login") // POST на этот URL для валидации.
                .defaultSuccessUrl("/")
                .permitAll());
        // TODO: Как сделать выход?
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll());

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
