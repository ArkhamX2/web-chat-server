package ru.arkham.webchat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import ru.arkham.webchat.controller.SecurityController;

/**
 * Конфигуратор бинов для модуля безопасности.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    /**
     * Менеджер авторизации.
     * @param authenticationConfiguration конфигурация авторизации.
     * @return менеджер авторизации.
     * @throws Exception при внутренней ошибке.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
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
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers(SecurityController.URL_HOME + "/**").permitAll()
                .anyRequest().authenticated());
        // TODO: Добавить фильтр авторизации через токены.
        http.exceptionHandling(configurer -> configurer
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement(configurer -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
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
