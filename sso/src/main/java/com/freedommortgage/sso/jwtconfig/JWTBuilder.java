package com.freedommortgage.sso.jwtconfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;

public class JWTBuilder {
    
    private static String signingKey="Temporary_Key";

    static KeyPair kp = RsaProvider.generateKeyPair();
    private static PublicKey publicKey = kp.getPublic();
    private static PrivateKey privateKey = kp.getPrivate();
    
    public static String generateJWT() {
        String token = Jwts.builder()
                .setIssuer("OAuth Server")
                .setAudience("www.example.com")
                .setSubject("plainUser")
                .claim("auth_time", LocalDate.now())
                .claim("auth_remoteip", "200.103.68.1")
                .claim("idp","http://localhost:8082/ui")
                .claim("acr","https://auth.conformx.com/.well-known/openid-configuration")
                .claim("azp", "http://localhost:8083/ui2")
                .signWith(SignatureAlgorithm.RS256,privateKey)
                .compact();
        return token;
    }
    
}
