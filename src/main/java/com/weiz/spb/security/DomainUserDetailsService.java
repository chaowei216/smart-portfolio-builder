package com.weiz.spb.security;

import com.weiz.spb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (new EmailValidator().isValid(username, null)) {
            return userRepository
                    .findByEmail(username)
                    .map(this::createSpringSecurityUser)
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
        }

        final var lowerCaseLogin = username.toLowerCase(Locale.ENGLISH);
        return userRepository
                .findByEmail(lowerCaseLogin)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User" + lowerCaseLogin + " not found"));
    }

    private User createSpringSecurityUser(com.weiz.spb.entities.User user) {
        return new User(user.getEmail(), user.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
