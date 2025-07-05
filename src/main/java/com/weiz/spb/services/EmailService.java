package com.weiz.spb.services;

import com.weiz.spb.entities.User;
import jakarta.validation.constraints.NotNull;

public interface EmailService {

    void sendTokenVerificationEmail(@NotNull User user, @NotNull String token);

    void sendTokenForgotPassword(@NotNull User user, @NotNull String token);
}
