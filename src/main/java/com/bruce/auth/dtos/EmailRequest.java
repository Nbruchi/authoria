package com.bruce.auth.dtos;

import java.util.UUID;

public class EmailRequest {
    private UUID clientId;
    private String email;
    private String verificationCode;

    public EmailRequest(UUID clientId, String email, String verificationCode) {
        this.clientId = clientId;
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
