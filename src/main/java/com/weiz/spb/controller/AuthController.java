package com.weiz.spb.controller;

import com.weiz.spb.services.AuthService;
import com.weiz.spb.services.dto.Response;
import com.weiz.spb.services.dto.request.auth.GetAuthenticatedUserRequestDTO;
import com.weiz.spb.services.dto.request.auth.LoginRequestDTO;
import com.weiz.spb.services.dto.request.auth.RefreshTokenRequestDTO;
import com.weiz.spb.services.dto.request.auth.RegistrationRequestDTO;
import com.weiz.spb.services.dto.response.auth.AuthenticationResponseDTO;
import com.weiz.spb.services.dto.response.auth.GetAuthenticatedUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@Tag(name = "auth-controller")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "[GUEST]_Login", description = "Authenticate user to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(path = "/login")
    public Response<AuthenticationResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @Operation(summary = "[GUEST]_Register", description = "Register an account in system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(path = "/register")
    public Response<AuthenticationResponseDTO> register(@Valid @RequestBody RegistrationRequestDTO request) {
        return authService.register(request);
    }

    @Operation(summary = "[USER]_Refresh Token", description = "Refresh to get new access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/refresh-token")
    public Response<AuthenticationResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO request) {
        return authService.refreshToken(request);
    }

    @Operation(summary = "[USER]_Get authenticated user", description = "Get authenticated user information in authentication context")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(path = "/me")
    @PreAuthorize("isAuthenticated()")
    public Response<GetAuthenticatedUserDTO> getMe() {
        return authService.getMe();
    }

    @Operation(summary = "[GUEST]_Logout", description = "Logout user from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/logout")
    public Response<Void> logout() {
        return authService.logout();
    }
}
