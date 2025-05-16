package com.bruce.auth.services;

import com.bruce.auth.models.Client;
import com.bruce.auth.models.CustomUserDetails;
import com.bruce.auth.repos.ClientRepo;

import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientUserDetailsService implements UserDetailsService {
    private final ClientRepo clientRepo;

    public ClientUserDetailsService(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));

        return new CustomUserDetails(client.getEmail(), client.getPassword(), client.getId(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + client.getRole().name())));
    }
    
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails){
            return ((CustomUserDetails) authentication.getPrincipal()).getId();
        }

        return null;
    }
}
