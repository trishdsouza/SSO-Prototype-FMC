package com.freedommortgage.services.signing.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.freedommortgage.services.signing.constants.JSONWebToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTHelper extends JSONWebToken {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.password}")
    private String password;

    @Value("${ip.address}")
    private String ipAddress;

    @Value("${file.path}")
    private String filePath;

    @Value("${client.id}")
    private String clientId;

    @Value("${docutech.stageauth}")
    private String docutechStageAuth;

    @Value("${claims.idp}")
    private String lenderDomain;

    @Value("${claims.acr}")
    private String acrStandard;

    public Key getSignatureKey() {

        logger.debug("START :: privateKeyRetrievalFromVault");
        Key key = null;
        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            final KeyStore ks = KeyStore.getInstance(KEY_FILE_FORMAT);
            ks.load(in, password.toCharArray());
            key = ks.getKey(ALIAS, password.toCharArray());
            logger.debug("END :: privateKeyRetrievalFromVault");

        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException ex) {
            logger.error("Error retrieving private key from vault.", ex);
        }

        return key;
    }

    public String generateJWT(final String url) {

        logger.debug("START :: buildJSONWebToken for URL");

        final Instant currentTime = Instant.now();
        final Instant expiredTime = Instant.now().plus(Duration.ofHours(HOURS_TO_EXPIRY));
        final long authTime = currentTime.getEpochSecond();
        logger.debug("populateTimeClaims currentTime={} expiredTime={} authTime={}", currentTime, expiredTime, authTime);

        final String id = UUID.randomUUID().toString().replace("-", "");
        logger.debug("randomUserId id={}", id);

        String token = "";

        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            final Key key = getSignatureKey();
            if (key == null) {
                final String errorMessage = "The Private key returned is null.";
                logger.error(errorMessage);
                throw new NullPointerException(errorMessage);
            }

            token = Jwts.builder().setHeaderParam(HEADER_PARAM, HEADER_PARAM_VALUE)
                    .setIssuer(clientId)
                    .setAudience(docutechStageAuth)
                    .setSubject("UserID:12345")
                    .setIssuedAt(Date.from(currentTime))
                    .setExpiration(Date.from(expiredTime))
                    .setNotBefore(Date.from(currentTime))
                    .setId(id)
                    .claim(AUTH_REMOTE_IP, ipAddress)
                    .claim(AUTH_TIME, authTime)
                    .claim(IDP, lenderDomain)
                    .claim(ACR, acrStandard)
                    .claim(AZP, url)
                    .signWith(SignatureAlgorithm.RS256, key).compact();
            logger.debug("JWT claims registered with the paramteres set");
            logger.debug("END :: buildJSONWebToken for URL={}", url);
        } catch (final IOException ex) {
            logger.error("Error in building JWT using claims.", ex);
        }

        return token;
    }
}
