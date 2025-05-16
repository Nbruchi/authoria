package com.bruce.auth.dtos;

import java.util.UUID;

import com.bruce.auth.models.Client;

public class LoginResponse {
    private UUID userId;
    private String username;
    private String token;


    // Constructor, getters, and setters
    public LoginResponse(Client client, String token) {
        this.userId = client.getId();
        this.username = client.getEmail(); // or firstName, depending on your preference
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}