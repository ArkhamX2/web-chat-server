package ru.arkham.webchat.configuration.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Фильтр авторизации с помощью JWT токенов.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Заголовок JWT токена.
     */
    private static final String TOKEN_HEADER = "Authorization";

    /**
     * Префикс JWT токена.
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Сервис работы с данными пользователей модуля безопасности.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Провайдер JWT токенов.
     */
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain chain) throws ServletException, IOException {
        try {
            getJwtFromRequest(request)
                    .flatMap(tokenProvider::parseToken)
                    .ifPresent(jws -> {
                        String username = jws.getBody().getSubject();
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
        } catch (Exception exception) {
            log.error("Ошибка авторизации пользователя!", exception);
        }

        chain.doFilter(request, response);
    }

    /**
     * Получить JWT строку с токеном из запроса.
     * @param request запрос.
     * @return JWT строка с токеном.
     */
    private Optional<String> getJwtFromRequest(@NotNull HttpServletRequest request) {
        String tokenHeader = request.getHeader(TOKEN_HEADER);

        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith(TOKEN_PREFIX)) {
            return Optional.of(tokenHeader.replace(TOKEN_PREFIX, ""));
        }

        return Optional.empty();
    }
}
