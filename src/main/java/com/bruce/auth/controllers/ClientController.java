package com.bruce.auth.controllers;

import com.bruce.auth.constants.ErrorMessages;
import com.bruce.auth.exceptions.UnauthorizedAccessException;
import com.bruce.auth.models.Client;
import com.bruce.auth.dtos.LoginRequest;
import com.bruce.auth.dtos.LoginResponse;
import com.bruce.auth.services.ClientServiceImpl;
import com.bruce.auth.services.ClientUserDetailsService;
import com.bruce.auth.services.ImageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientServiceImpl clientService;
    private final ImageService imageService;
    private final ClientUserDetailsService userDetailsService;

    public ClientController(ClientServiceImpl clientService, ClientUserDetailsService userDetailsService, ImageService imageService) {
        this.imageService = imageService;
        this.clientService = clientService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        try {
            Client registeredClient = clientService.register(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredClient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = clientService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> signOut(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            clientService.signOut(jwtToken);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        if (hasAdminRole()){
            try {
                List<Client> clients = clientService.getAllClients();
                return ResponseEntity.ok(clients);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            throw new UnauthorizedAccessException(ErrorMessages.Client.UNAUTHORIZED_ACCESS);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable UUID id) {
        UUID currentUserId = userDetailsService.getCurrentUserId();
        if (currentUserId == null || (!currentUserId.equals(id) && !hasAdminRole())) {
            throw new UnauthorizedAccessException(ErrorMessages.Client.ONLY_SELF_ACCESS);
        }

        return clientService.getClient(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.Client.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @Valid @RequestBody Client client) {
        UUID currentUserId = userDetailsService.getCurrentUserId();
        if (currentUserId == null || (!currentUserId.equals(id) && !hasAdminRole())) {
            throw new UnauthorizedAccessException(ErrorMessages.Client.ONLY_SELF_UPDATE);
        }

        return clientService.updateClient(id, client)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.Client.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        UUID currentUserId = userDetailsService.getCurrentUserId();
        if (currentUserId == null || (!currentUserId.equals(id) && !hasAdminRole())) {
            throw new UnauthorizedAccessException(ErrorMessages.Client.ONLY_SELF_DELETE);
        }else {
            try {
                clientService.deleteClient(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

    @PostMapping("/{clientId}/images")
    public ResponseEntity<String> uploadImage(@PathVariable UUID clientId, @RequestParam("image")MultipartFile image) throws IOException {
        Client client = clientService.getClient(clientId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.Client.NOT_FOUND));

        if (client == null){
            return ResponseEntity.notFound().build();
        }else{
           String publicId = imageService.uploadImage((File) image);
           client.setPublicId(publicId);
           clientService.updateClient(clientId,client);

           return ResponseEntity.ok(publicId);
        }
    }

    @GetMapping("/{clientId}/images")
    public ResponseEntity<String> getImage(@PathVariable UUID clientId){
        Client client = clientService.getClient(clientId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.Client.NOT_FOUND));

        if (client == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(client.getPublicId());
        }
    }

    @PutMapping("/{clientId}/images")
    public ResponseEntity<String> updateImage(@PathVariable UUID clientId, @RequestParam("image") MultipartFile image) throws IOException {
        Client client = clientService.getClient(clientId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.Client.NOT_FOUND));


        if (client == null){
            return ResponseEntity.notFound().build();
        }else{
            String publicId = client.getPublicId();
            String newPublicId = imageService.updateImage(publicId, (File) image);
            client.setPublicId(newPublicId);
            clientService.updateClient(clientId,client);

            return ResponseEntity.ok(newPublicId);
        }
    }

    @DeleteMapping("/{clientId}/images")
    public ResponseEntity<String> deleteImage(@PathVariable UUID clientId) throws IOException {
        Client client = clientService.getClient(clientId).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.Client.NOT_FOUND));

        if (client == null){
            return ResponseEntity.notFound().build();
        }else{
            String publicId = client.getPublicId();
            imageService.deleteImage(publicId);
            client.setPublicId(null);
            clientService.updateClient(clientId,client);

            return ResponseEntity.ok("Image deleted");
        }
    }

    public boolean hasAdminRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_ADMIN")
        );
    }
}
