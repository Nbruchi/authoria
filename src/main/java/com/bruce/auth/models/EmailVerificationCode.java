package com.bruce.auth.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "codes")
public class EmailVerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String code;

    @OneToOne(targetEntity = Client.class)
    @JoinColumn(nullable = false,name = "client_id")
    private Client client;

    @Column(nullable = false)
    private String email;

    private Boolean isUsed = false;
    private LocalDateTime verifiedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public EmailVerificationCode(){
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(5);
    }

    public EmailVerificationCode(String code, Client client, String email, Boolean isUsed) {
        this.code = code;
        this.client = client;
        this.email = email;
        this.isUsed = isUsed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
