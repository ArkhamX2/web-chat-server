package ru.arkham.webchat.configuration.component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class TokenProvider {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "server";
    public static final String TOKEN_AUDIENCE = "client";

    private static final String TOKEN_SECRET = "707";
    private static final Long TOKEN_EXPIRATION_MINUTES = (long) (60 * 12);

    /**
     * Сгенерировать токен.
     * @param authentication токен авторизации.
     * @return токен.
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        byte[] signingKey = TOKEN_SECRET.getBytes();

        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES).toInstant()))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(userDetails.getUsername())
                .claim("rol", roles)
                .claim("name", userDetails.getUsername())
                .compact();
    }

    /**
     * Спарсить и получить JWT токен.
     * @param token токен.
     * @return JWT токен.
     */
    public Optional<Jws<Claims>> getJws(String token) {
        try {
            byte[] signingKey = TOKEN_SECRET.getBytes();

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Запрос на парсинг просроченного JWT: {} провалился: {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Запрос на парсинг неподдерживаемого JWT: {} провалился: {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Запрос на парсинг недействительного JWT: {} провалился: {}", token, exception.getMessage());
        } catch (SecurityException exception) {
            log.error("Запрос на парсинг JWT с недействительной сигнатурой: {} провалился: {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Запрос на парсинг пустого JWT: {} провалился: {}", token, exception.getMessage());
        }

        return Optional.empty();
    }
}
