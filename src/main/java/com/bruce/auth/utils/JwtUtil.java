package com.bruce.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return createToken(new HashMap<>(), userDetails);
    }

    public String createToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return buildToken(extractClaims, userDetails,jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(), userDetails,refreshExpiration);
    }

    private String buildToken(Map<String,Object> extractClaims, UserDetails userDetails, Long expiration){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
