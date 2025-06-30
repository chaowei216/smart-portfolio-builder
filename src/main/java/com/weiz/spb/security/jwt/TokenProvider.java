package com.weiz.spb.security.jwt;

import com.weiz.spb.common.constants.AppConst;
import com.weiz.spb.config.properties.JwtProperties;
import com.weiz.spb.entities.Token;
import com.weiz.spb.entities.enums.TokenType;
import com.weiz.spb.exception.BadRequestException;
import com.weiz.spb.exception.NotFoundException;
import com.weiz.spb.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token";

    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";

    private static final String INVALID_TOKEN = "Invalid token";

    private final SecretKey key;

    private final JwtParser jwtParser;

    private final JwtProperties jwtProperties;

    private final TokenRepository tokenRepository;

    public TokenProvider(JwtProperties jwtProperties, TokenRepository tokenRepository) {

        this.tokenRepository = tokenRepository;
        this.jwtProperties = jwtProperties;

        // set secret key
        byte[] keyBytes;
        var secret = jwtProperties.getSecretKey();
        keyBytes = Decoders.BASE64URL.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts
                .parser()
                .verifyWith(key)
                .build();
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + 1000L * jwtProperties.getExpiration());

        return Jwts
                .builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .expiration(validity)
                .compact();
    }

    public Token generateRefreshToken(com.weiz.spb.entities.User user) {

        // revoke existing token
        final var tokens = tokenRepository.findByUser(user);

        if (!tokens.isEmpty()) {
            tokens.forEach(this::revokeToken);

            // save all changes
            tokenRepository.saveAll(tokens);
        }

        // generate token
        Token token = Token.builder()
                .user(user)
                .tokenValue(UUID.randomUUID().toString())
                .tokenTypeDescription(TokenType.REFRESH_TOKEN.name())
                .isRevoked(false)
                .expirationDate(Instant.now().plusMillis(1000L * jwtProperties.getRefreshDuration())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .tokenType(TokenType.REFRESH_TOKEN)
                .build();

        // save to db
        tokenRepository.save(token);

        return token;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map("ROLE_"::concat)
                .map(SimpleGrantedAuthority::new)
                .toList();

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Optional<Token> getRefreshToken(String token) {
        return tokenRepository.findByTokenValue(token);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            throw new BadCredentialsException(INVALID_JWT_TOKEN);
        }
    }

    public void validateRefreshToken(Token token) {

        if (token.isRevoked())
            throw new BadCredentialsException(INVALID_REFRESH_TOKEN);

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            revokeToken(token);
            throw new BadRequestException(INVALID_REFRESH_TOKEN);
        }
    }

    /**
     * generate verify token
     * @param user user need verify email
     * @return token 6-digit
     */
    public Token generateVerifyToken(com.weiz.spb.entities.User user) {
        final var existingTokens = user.getTokens()
                .stream()
                .filter(t -> !t.isRevoked() && t.getExpirationDate().isAfter(LocalDateTime.now())
                        && t.getTokenType() == TokenType.EMAIL_VERIFICATION_TOKEN).collect(Collectors.toSet());

        // revoke token
        existingTokens.forEach(this::revokeToken);

        final var token = generateToken();

        Token dbToken = Token.builder()
                .user(user)
                .tokenType(TokenType.EMAIL_VERIFICATION_TOKEN)
                .tokenTypeDescription(TokenType.EMAIL_VERIFICATION_TOKEN.name())
                .tokenValue(token)
                .isRevoked(false)
                .expirationDate(Instant.now().plusMillis(1000 * 60 * 15)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        tokenRepository.save(dbToken);
        return dbToken;
    }

    /**
     * generate forgot pass token
     * @param user user need send reset pass token
     * @return token 6-digit
     */
    public Token generateForgotPasswordCode(com.weiz.spb.entities.User user) {
        final var existingTokens = user.getTokens()
                .stream()
                .filter(t -> !t.isRevoked() && t.getExpirationDate().isAfter(LocalDateTime.now())
                        && t.getTokenType() == TokenType.FORGOT_PASSWORD_TOKEN).collect(Collectors.toSet());

        // revoke token
        existingTokens.forEach(this::revokeToken);

        final var token = generateToken();

        Token dbToken = Token.builder()
                .user(user)
                .tokenType(TokenType.FORGOT_PASSWORD_TOKEN)
                .tokenTypeDescription(TokenType.FORGOT_PASSWORD_TOKEN.name())
                .tokenValue(token)
                .isRevoked(false)
                .expirationDate(Instant.now().plusMillis(1000 * 60 * 15)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        tokenRepository.save(dbToken);
        return dbToken;
    }

    public Token getVerifyToken(String token) {
        return tokenRepository.findByTokenValue(token).orElse(null);
    }

    public void validateDbToken(String token) {
        final var dbToken = getVerifyToken(token);


        if (dbToken == null) {
            throw new NotFoundException(INVALID_TOKEN);
        }

        if (dbToken.isRevoked()) {
            revokeToken(dbToken);
            throw new BadRequestException(INVALID_TOKEN);
        }

        if (dbToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            revokeToken(dbToken);
            throw new BadRequestException(INVALID_TOKEN);
        }

        revokeToken(dbToken);
    }

    public void revokeToken(Token token) {
        token.setRevoked(true);
        token.setRevokedAt(LocalDateTime.now());
        tokenRepository.save(token);
    }

    /**
     * Generate a token
     *
     * @return token included 6 digits
     */
    private String generateToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000);
        return String.valueOf(token);
    }
}
