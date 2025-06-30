package com.weiz.spb.services.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be in valid form")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be from 8 to 20 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(max = 30)
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phoneNumber;
}
