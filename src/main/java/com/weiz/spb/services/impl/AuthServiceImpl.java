package com.weiz.spb.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiz.spb.common.constants.AppConst;
import com.weiz.spb.entities.User;
import com.weiz.spb.entities.enums.TokenType;
import com.weiz.spb.exception.BadRequestException;
import com.weiz.spb.exception.NotFoundException;
import com.weiz.spb.exception.ResourceConflictException;
import com.weiz.spb.exception.UnauthorizedException;
import com.weiz.spb.repositories.UserRepository;
import com.weiz.spb.security.jwt.TokenProvider;
import com.weiz.spb.services.AuthService;
import com.weiz.spb.services.EmailService;
import com.weiz.spb.services.dto.Response;
import com.weiz.spb.services.dto.request.auth.*;
import com.weiz.spb.services.dto.response.auth.AuthenticationResponseDTO;
import com.weiz.spb.services.dto.response.auth.GetAuthenticatedUserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final EmailService emailService;

    @Override
    @Transactional
    public Response<AuthenticationResponseDTO> login(@NotNull LoginRequestDTO request) {

        // create an instance
        final var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        // authenticate
        final var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // set auth to context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // find by email
        var user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            // generate token
            AuthenticationResponseDTO responseDTO = AuthenticationResponseDTO.builder()
                    .accessToken(tokenProvider.generateToken(authentication))
                    .refreshToken(tokenProvider.generateRefreshToken(user.get()).getTokenValue())
                    .build();

            // set last login
            var actualUser = user.get();
            actualUser.setLastLogin(LocalDateTime.now());
            userRepository.save(actualUser);

            return Response.success(responseDTO, AppConst.AUTH_LOGIN_SUCCESS);
        }

        return null;
    }

    @Override
    @Transactional
    public Response<AuthenticationResponseDTO> register(@NotNull RegistrationRequestDTO request) {

        // find by email
        var user = userRepository.findByEmailOrPhoneNumber(request.getEmail(), request.getPhoneNumber());

        if (user.isPresent()) {
            throw new ResourceConflictException(AppConst.AUTH_REGISTER_USER_EXISTS);
        }

        // save user
        String passwordHash = passwordEncoder.encode(request.getPassword());

        var createdUser = objectMapper.convertValue(request, User.class);
        createdUser.setPasswordHash(passwordHash);
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setVerified(false);

        userRepository.save(createdUser);

        // generate token
        // create an instance
        final var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        // authenticate
        final var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // set auth to context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthenticationResponseDTO responseDTO = AuthenticationResponseDTO.builder()
                .accessToken(tokenProvider.generateToken(authentication))
                .refreshToken(tokenProvider.generateRefreshToken(createdUser).getTokenValue())
                .build();

        return Response.success(responseDTO, AppConst.AUTH_LOGIN_SUCCESS);
    }

    @Override
    @Transactional
    public Response<AuthenticationResponseDTO> refreshToken(RefreshTokenRequestDTO request) {

        // check if token is existed in db
        final var token = tokenProvider.getRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new BadRequestException(AppConst.AUTH_TOKEN_INVALID));

        // validate token
        tokenProvider.validateRefreshToken(token);

        // return value
        AuthenticationResponseDTO responseDTO = AuthenticationResponseDTO.builder()
                .accessToken(tokenProvider.generateToken(SecurityContextHolder.getContext().getAuthentication()))
                .refreshToken(tokenProvider.generateRefreshToken(token.getUser()).getTokenValue())
                .build();

        return Response.success(responseDTO, AppConst.AUTH_REFRESH_TOKEN_SUCCESS);
    }

    @Override
    @Transactional
    public Response<Void> logout() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException(AppConst.UNAUTHORIZED_USER);
        }

        // get user by id
        final var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException(AppConst.NOT_FOUND.getMessage()));

        // revoke refresh token
        user.getTokens().stream()
                .filter(token -> token.getTokenType()
                        .equals(TokenType.REFRESH_TOKEN) &&
                        !token.isRevoked())
                .findFirst()
                .ifPresent(tokenProvider::revokeToken);

        return Response.success(null, AppConst.AUTH_LOGOUT_SUCCESS);
    }

    @Override
    public Response<GetAuthenticatedUserDTO> getMe() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException(AppConst.UNAUTHORIZED_USER);
        }

        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User with email " + authentication.getName() + " " + AppConst.NOT_FOUND.getMessage()));

        var result = objectMapper.convertValue(user, GetAuthenticatedUserDTO.class);

        return Response.success(result, AppConst.GET_SUCCESS);
    }

    @Override
    public Response<Void> verifyEmail(VerificationRequestDTO request) {

        // find user
        final var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email: " + request.getEmail() + AppConst.NOT_FOUND.getMessage()));

        // check status
        if (user.isVerified()) {
            throw new ResourceConflictException(AppConst.AUTH_VERIFIED_EMAIL);
        }

        // generate random token 6-digits
        final var token = tokenProvider.generateVerifyToken(user);

        emailService.sendTokenVerificationEmail(user, token.getTokenValue());

        return Response.success(null, AppConst.AUTH_SEND_VERIFY_CODE_SUCCESS);
    }

    @Override
    public Response<Void> verifyPassword(VerificationRequestDTO request) {

        // get user by email
        final var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email: " + request.getEmail() + AppConst.NOT_FOUND.getMessage()));

        // check status
        if (!user.isVerified()) {
            throw new BadRequestException(AppConst.AUTH_UNVERIFIED_EMAIL);
        }

        // generate random token 6-digits
        final var token = tokenProvider.generateForgotPasswordCode(user);

        // send email
        emailService.sendTokenForgotPassword(user, token.getTokenValue());

        return Response.success(null, AppConst.AUTH_SEND_VERIFY_CODE_SUCCESS);
    }

    @Override
    @Transactional
    public Response<Void> confirmEmail(ConfirmEmailRequestDTO request) {

        // get user by email
        final var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email: " + request.getEmail() + AppConst.NOT_FOUND.getMessage()));

        // check status
        if (user.isVerified()) {
            throw new ResourceConflictException(AppConst.AUTH_VERIFIED_EMAIL);
        }

        // check token
        tokenProvider.validateDbToken(request.getCode());

        // update status
        user.setVerified(true);

        // save to db
        userRepository.save(user);

        return Response.success(null, AppConst.AUTH_VERIFY_EMAIL_SUCCESS);
    }

    @Override
    @Transactional
    public Response<Void> resetPassword(ResetPasswordRequestDTO request) {

        // get user by email
        final var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email: " + request.getEmail() + AppConst.NOT_FOUND.getMessage()));

        // check token
        tokenProvider.validateDbToken(request.getCode());

        // update password
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));

        // save to db
        userRepository.save(user);

        return Response.success(null, AppConst.AUTH_RESET_PASSWORD_SUCCESS);
    }
}
