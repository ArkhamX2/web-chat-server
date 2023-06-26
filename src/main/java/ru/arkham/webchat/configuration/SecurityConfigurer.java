package ru.arkham.webchat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.arkham.webchat.controller.SecurityController;

/**
 * Конфигуратор бинов для модуля безопасности.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    /**
     * Сервис загрузки данных пользователей.
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
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers(SecurityController.URL_HOME_REGISTER).permitAll()
                .anyRequest().authenticated());
        http.formLogin(configurer -> configurer
                .loginPage(SecurityController.URL_HOME_LOGIN) // GET на этот URL для обработки входа.
                .loginProcessingUrl(SecurityController.URL_HOME_LOGIN) // POST на этот URL для авторизации.
                .usernameParameter("username") // Параметр для передачи имени пользователя в POST.
                .passwordParameter("password") // Параметр для передачи пароля в POST.
                .failureUrl(SecurityController.URL_HOME_LOGIN + "?error") // Дополнительный параметр при ошибке.
                .defaultSuccessUrl("/")
                .permitAll());
        http.logout(configurer -> configurer
                .logoutUrl(SecurityController.URL_HOME + "/logout") // POST на этот URL для выхода.
                .logoutSuccessUrl(SecurityController.URL_HOME_LOGIN + "?logout") // Дополнительный параметр при выходе.
                .permitAll());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

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
