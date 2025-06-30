package com.weiz.spb.services;

import com.weiz.spb.services.dto.Response;
import com.weiz.spb.services.dto.request.auth.GetAuthenticatedUserRequestDTO;
import com.weiz.spb.services.dto.request.auth.LoginRequestDTO;
import com.weiz.spb.services.dto.request.auth.RefreshTokenRequestDTO;
import com.weiz.spb.services.dto.request.auth.RegistrationRequestDTO;
import com.weiz.spb.services.dto.response.auth.AuthenticationResponseDTO;
import com.weiz.spb.services.dto.response.auth.GetAuthenticatedUserDTO;
import jakarta.validation.constraints.NotNull;

public interface AuthService {

    Response<AuthenticationResponseDTO> login(@NotNull LoginRequestDTO request);

    Response<AuthenticationResponseDTO> register(@NotNull RegistrationRequestDTO request);

    Response<AuthenticationResponseDTO> refreshToken(@NotNull RefreshTokenRequestDTO request);

    Response<Void> logout();

    Response<GetAuthenticatedUserDTO> getMe();
}
