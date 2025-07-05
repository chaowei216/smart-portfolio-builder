package com.weiz.spb.services;

import com.weiz.spb.services.dto.Response;
import com.weiz.spb.services.dto.request.auth.*;
import com.weiz.spb.services.dto.response.auth.AuthenticationResponseDTO;
import com.weiz.spb.services.dto.response.auth.GetAuthenticatedUserDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public interface AuthService {

    Response<AuthenticationResponseDTO> login(@NotNull LoginRequestDTO request);

    Response<AuthenticationResponseDTO> register(@NotNull RegistrationRequestDTO request);

    Response<AuthenticationResponseDTO> refreshToken(@NotNull RefreshTokenRequestDTO request);

    Response<Void> logout();

    Response<GetAuthenticatedUserDTO> getMe();

    Response<Void> verifyEmail(@NotNull VerificationRequestDTO request);

    Response<Void> verifyPassword(@NotNull VerificationRequestDTO request);

    Response<Void> confirmEmail(@NotNull ConfirmEmailRequestDTO request);

    Response<Void> resetPassword(@NotNull ResetPasswordRequestDTO request);
}
