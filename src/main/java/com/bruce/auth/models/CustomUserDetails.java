package com.bruce.auth.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails extends User {
    private final UUID id;

    public CustomUserDetails(
        String username, 
        String password, 
        UUID id,
        Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}