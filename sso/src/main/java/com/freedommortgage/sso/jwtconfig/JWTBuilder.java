package com.freedommortgage.sso.jwtconfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTBuilder {
    
//    private static String signingKey="Temporary_Key";

    KeyPair kp = RsaProvider.generateKeyPair();
    private PublicKey publicKey = kp.getPublic();
    private PrivateKey privateKey = kp.getPrivate();

    Instant currentTime = Instant.now();
    Instant expiredTime = Instant.now().plus(Duration.ofHours(1));
    Instant authTime = Instant.now().minus(Duration.ofMinutes(4));


//    Key privateKey = loadPrivateKey(new FileInputStream("./keys/private_key.pem"));
//    Key publicKey = loadPublicKey(new FileInputStream("./keys/public_key.pem"));

    String id = UUID.randomUUID().toString().replace("-", "");

    public String generateJWT() {
        String token = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setIssuer("FMC Client ID")
                .setAudience("https://stageauth.conformx.com")
                .setSubject("UserID:12345")
                .setIssuedAt(Date.from(currentTime))
                .setExpiration(Date.from(expiredTime))
                .setNotBefore(Date.from(currentTime))
                .setId(id)
                .claim("auth_time", Date.from(authTime))
                .claim("auth_remoteip","200.103.68.1")
                .claim("idp","https://FMC.com")
                .claim("acr","https://auth.conformx.com/policy/acr/1")
                .claim("azp","https://stage.solex.com/welcome/12345678-1234-1234-1234-123412341234")
                .signWith(SignatureAlgorithm.RS256,privateKey)
                .compact();
        return token;
    }
    
}
