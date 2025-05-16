package com.bruce.auth.repos;

import com.bruce.auth.models.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationCodeRepo extends JpaRepository<EmailVerificationCode, UUID> {
    Optional<EmailVerificationCode> findByClientIdAndEmailAndCode(
            UUID clientId,
            String email,
            String code
    );

    Optional<EmailVerificationCode> findByClientIdAndEmailAndIsUsedFalse(
            UUID clientId,
            String email
    );
}
