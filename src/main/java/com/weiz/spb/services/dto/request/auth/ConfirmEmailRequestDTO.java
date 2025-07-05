package com.weiz.spb.services.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ConfirmEmailRequestDTO {

    @Email(message = "Email must be in valid form")
    @NotBlank
    private String email;

    @NotNull
    private String code;
}
