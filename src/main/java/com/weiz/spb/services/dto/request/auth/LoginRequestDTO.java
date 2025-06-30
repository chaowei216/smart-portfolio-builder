package com.weiz.spb.services.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be in valid form")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
