package ru.arkham.webchat.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Конфигуратор бинов для модуля CORS.
 */
@RequiredArgsConstructor
@Configuration
public class CorsConfigurer {

    /**
     * Разрешенный источник.
     */
    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;

    /**
     * Конфигурация CORS.
     * @return конфигурация CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // TODO: Протестировать.
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // TODO: Разместить все пути в одном месте.
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
