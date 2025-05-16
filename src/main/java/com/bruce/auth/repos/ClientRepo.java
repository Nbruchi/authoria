package com.bruce.auth.repos;

import com.bruce.auth.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepo extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
}
