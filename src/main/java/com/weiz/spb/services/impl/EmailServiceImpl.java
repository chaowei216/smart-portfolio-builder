package com.weiz.spb.services.impl;

import com.weiz.spb.common.constants.AppConst;
import com.weiz.spb.entities.User;
import com.weiz.spb.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    public void sendTokenVerificationEmail(User user, String token) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            // load template email with content
            Context context = new Context();
            context.setVariable("userName", user.getFullName());
            context.setVariable("token", token);
            String html = templateEngine.process("verify-email", context);

            // send email
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject(AppConst.VERIFY_EMAIL_SUBJECT);
            helper.setFrom(AppConst.EMAIL_SEND);
            mailSender.send(mimeMessage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @Async
    public void sendTokenForgotPassword(User user, String token) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            // load template email with content
            Context context = new Context();
            context.setVariable("fullName", user.getFullName());
            context.setVariable("token", token);
            String html = templateEngine.process("forgot-password", context);

            // send email
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject(AppConst.FORGET_PASSWORD_SUBJECT);
            helper.setFrom(AppConst.EMAIL_SEND);
            mailSender.send(mimeMessage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
