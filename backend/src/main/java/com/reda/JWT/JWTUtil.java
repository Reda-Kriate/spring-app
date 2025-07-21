package com.reda.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class JWTUtil {
    //Signature de generation Token
    private static final String SECRET_KEY =
            "redakriate_123456789_redakriate_123456789_redakriate_123456789_redakriate_123456789";
    public String issueToken(String subject){
        return issueToken(subject,Map.of());
    }
//Creation de token avec scopes (ROLE...)
    public String issueToken(String subject, String ...scopes){
        return issueToken(subject,Map.of("scopes",scopes));
    }
//Creation token d'authorization
    public String issueToken(String subject , Map<String,Object> claims){
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("https://redakriate.com")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now().plus(15, ChronoUnit.DAYS)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
    //Extraire tous les claims (= le contenu du token) / utilise parseClaimsJws pour verifier token
    public Claims getClaims(String token){
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
// Utilise getClaims() pour extraire tous les claims,
// puis retourne .getSubject() (= subject c'est le username dans notre cas c'est l'email ).
    public String getSubject(String token){
        return getClaims(token).getSubject();
    }
    //Génère la clé secrète à partir de SECRET_KEY.
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }
}
