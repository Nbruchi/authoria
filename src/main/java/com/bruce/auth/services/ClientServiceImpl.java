package com.bruce.auth.services;

import com.bruce.auth.dtos.LoginResponse;
import com.bruce.auth.models.Client;
import com.bruce.auth.repos.ClientRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import com.bruce.auth.utils.JwtUtil;

@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepo clientRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final TokenBlacklistService tokenService;
    private final JwtUtil jwtUtil;

    public ClientServiceImpl(ClientRepo clientRepo, PasswordEncoder encoder, AuthenticationManager authManager, TokenBlacklistService tokenService, JwtUtil jwtUtil){
        this.clientRepo = clientRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Client register(Client client) {
        client.setPassword(encoder.encode(client.getPassword()));
        return clientRepo.save(client);
    }

    @Override
    public LoginResponse login(String username, String password) {
        // Authenticate the user
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        
        // Retrieve the client
        Client client = clientRepo.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
        
        // Generate JWT token
        UserDetails userDetails = new User(client.getEmail(), client.getPassword(), new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        
        // Return login response with minimal information
        return new LoginResponse(client, token);
    }

    @Override
    public void signOut(String token) {
        tokenService.addToken(token);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    @Override
    public Optional<Client> getClient(UUID id) {
        return clientRepo.findById(id);
    }

    @Override
    public Optional<Client> updateClient(UUID id, Client client) {
       return clientRepo.findById(id).map(existingClient ->{
           existingClient.setFirstName(client.getFirstName());
           existingClient.setLastName(client.getLastName());

           return clientRepo.save(existingClient);
       });
    }

    @Override
    public void deleteClient(UUID id) {
        clientRepo.deleteById(id);
    }
}
