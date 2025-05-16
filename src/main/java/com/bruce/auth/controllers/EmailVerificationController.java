package com.bruce.auth.controllers;

import com.bruce.auth.constants.ErrorMessages;
import com.bruce.auth.dtos.EmailRequest;
import com.bruce.auth.dtos.VerifyEmailRequest;
import com.bruce.auth.exceptions.UnauthorizedAccessException;
import com.bruce.auth.models.EmailVerificationCode;
import com.bruce.auth.services.ClientUserDetailsService;
import com.bruce.auth.services.EmailVerificationCodeService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/clients/emails")
public class EmailVerificationController {
    private final ClientUserDetailsService userDetailsService;
    private final EmailVerificationCodeService emailService;

    public EmailVerificationController(ClientUserDetailsService userDetailsService, EmailVerificationCodeService emailService) {
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    @PostMapping("/{clientId}/verify-code")
    public ResponseEntity<?> initiateEmailVerification(
            @PathVariable UUID clientId, @Valid @RequestBody EmailRequest request
    ) throws MessagingException, IOException {
        UUID currentUserId = userDetailsService.getCurrentUserId();

        if (currentUserId == null || (!currentUserId.equals(clientId))){
            throw new UnauthorizedAccessException(ErrorMessages.Client.UNAUTHORIZED_ACCESS);
        }

        EmailVerificationCode verificationCode = emailService.initiateEmailVerification(clientId, request.getEmail());
        return ResponseEntity.ok("Verification code sent");
    }

    @PostMapping("/confirm-code")
    public ResponseEntity<?> verifyEmailCode(@Valid @RequestBody EmailRequest request) {
        UUID currentUserId = userDetailsService.getCurrentUserId();

        if (currentUserId == null || (!currentUserId.equals(request.getClientId()))){
            throw new UnauthorizedAccessException(ErrorMessages.Client.UNAUTHORIZED_ACCESS);
        }

        emailService.verifyEmailCode(request.getClientId(), request.getEmail(), request.getVerificationCode());
        return ResponseEntity.ok("Email verified");
    }

    @PostMapping("/{clientId}/verify-email")
    public ResponseEntity<?> verifyEmail(@PathVariable UUID clientId, @RequestBody EmailRequest request){
        emailService.verifyEmail(clientId,request.getEmail());
        return ResponseEntity.ok("Verification code sent!");
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@Valid @RequestBody VerifyEmailRequest request){
        emailService.confirmEmailVerification(request.getClientId(), request.getEmail(),request.getCode());
        return ResponseEntity.ok("Email verified!");
    }
}
