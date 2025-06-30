package com.weiz.spb.services.dto.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationResponseDTO {

    private String accessToken;
    private String refreshToken;
}
