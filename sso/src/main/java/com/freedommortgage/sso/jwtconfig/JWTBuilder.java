package com.freedommortgage.sso.jwtconfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JWTBuilder {
    
    private String signingKey="Temporary_Key";
    
    public String generateJWT() {
        String token = Jwts.builder()
                .setIssuer("OAuth Server")
                .setAudience("www.example.com")
                .setSubject("plainUser")
                .claim("auth_time", LocalDate.now())
                .claim("auth_remoteip", "200.103.68.1")
                .claim("idp","http://localhost:8082/ui")
                .claim("acr","https://auth.conformx.com/.well-known/openid-configuration")
                .claim("azp", "http://localhost:8083/ui2")
                .signWith(SignatureAlgorithm.RS256,signingKey)
                .compact();
        return token;
    }
    
}
