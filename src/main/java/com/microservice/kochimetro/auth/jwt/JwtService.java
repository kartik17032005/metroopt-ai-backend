package com.microservice.kochimetro.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/*
Claims are just like the id card
the contains information like subject (who jwt must be issued)
issued at -> time, expiration time -> when will expire etc
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Getter
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    //jwt needs a key to sign tokens and verify tokens
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //generating the token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact(); //convert everything into the long jwt string

    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser() // token -> parser
                .verifyWith(getSigningKey()) //signed with the secret key in generate token now here verifying with the same key
                .build()
                .parseSignedClaims(token) // reads , is signature valid, token expired ?
                .getPayload(); //returs map<string, object>
        /*{
           "sub":"kartik@gmail.com",
           "iat":...,
           "exp":...
         */
    }

    //instead of writing claims.getSubject();claims.getSubject();  we created one generic method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    //extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //extract expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //the token belongs to the same user
    //the token hasn't expired
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
