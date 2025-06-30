package com.weiz.spb.services.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RefreshTokenRequestDTO {

    @NotBlank(message = "Refresh Token is required")
    private String refreshToken;
}
