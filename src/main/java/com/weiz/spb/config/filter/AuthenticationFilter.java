package com.weiz.spb.config.filter;

import com.weiz.spb.config.SecurityConfig;
import com.weiz.spb.security.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter extends OncePerRequestFilter {

    static String AUTHORIZATION_HEADER = "Authorization";
    static String AUTHORIZATION_TOKEN = "access_token";

    TokenProvider tokenProvider;

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();

        return Arrays.stream(SecurityConfig.PUBLIC_APIS)
                .anyMatch(pattern -> matcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = getAccessToken(request);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {

        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        String jwt = request.getParameter(AUTHORIZATION_TOKEN);

        if (StringUtils.hasText(jwt)) {
            return jwt;
        }

        return null;
    }
}
