package com.weiz.spb.services.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAuthenticatedUserRequestDTO {

    @NotBlank(message = "Access token is required")
    private String accessToken;
}
