package com.weiz.spb.services.dto.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetAuthenticatedUserDTO {

    private String id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private boolean isActive;

    private LocalDateTime lastLogin;
}
