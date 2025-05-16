package com.bruce.auth.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {
    private final Set<String> blacklist = new HashSet<>();

    public void addToken(String token){
        blacklist.add(token);
    }

    public void removeToken(String token){
        blacklist.remove(token);
    }

    public boolean isBlacklisted(String token){
        return blacklist.contains(token);
    }
}
