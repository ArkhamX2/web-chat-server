package ru.arkham.webchat.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();

        // TODO: Протестировать.
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("HEAD");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");

        // TODO: Разместить все пути в одном месте.
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}
