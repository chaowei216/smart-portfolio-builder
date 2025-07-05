package com.weiz.spb.services.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPasswordRequestDTO {

    @Email(message = "Email must be in valid form")
    @NotNull
    private String email;

    @NotNull
    private String code;

    @NotNull
    private String newPassword;
}
