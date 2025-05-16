package com.bruce.auth.dtos;

import java.util.UUID;

public class VerifyEmailRequest {
    private UUID clientId;
    private String email;
    private String code;

    public VerifyEmailRequest(UUID clientId, String email, String code) {
        this.clientId = clientId;
        this.email = email;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
