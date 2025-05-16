package com.bruce.auth.services;

import com.bruce.auth.dtos.LoginResponse;
import com.bruce.auth.models.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ClientService {
    Client register(Client client);

    LoginResponse login(String username, String password);

    void signOut(String token);

    List<Client> getAllClients();

    Optional<Client> getClient(UUID id);

    Optional<Client> updateClient(UUID id,Client client);

    void deleteClient(UUID id);
}
