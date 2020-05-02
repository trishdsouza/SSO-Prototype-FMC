package com.freedommortgage.sso.jwtconfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;

@Component
public class JWTBuilder {

//    private static String signingKey="Temporary_Key";

//    KeyPair kp = RsaProvider.generateKeyPair();
//    private PublicKey publicKey = kp.getPublic();
//    private PrivateKey key = kp.getPrivate();

	Instant currentTime = Instant.now();
	Instant expiredTime = Instant.now().plus(Duration.ofHours(1));
	Instant authTime = Instant.now().minus(Duration.ofMinutes(4));


	String jksPassword = "padsouza";

	String id = UUID.randomUUID().toString().replace("-", "");

	public String generateJWT() throws Exception {

		KeyStore ks = KeyStore.getInstance("PKCS12");

		FileInputStream fs = new FileInputStream("D:/Users/padsouza/Desktop/Certs/FMTest.pfx");

		ks.load(fs, jksPassword.toCharArray());

		Enumeration<String> es = ks.aliases();
		while (es.hasMoreElements()) {
			String alias = (String) es.nextElement();
			System.out.println(alias);
			if (ks.getCertificate(alias).getType().equals("X.509")) {
				Date expDate = ((X509Certificate) ks.getCertificate(alias)).getNotAfter();
				Date fromDate = ((X509Certificate) ks.getCertificate(alias)).getNotBefore();
				System.out.println("Expiry Date:-" + expDate);
				System.out.println("From Date:-" + fromDate);
			}
		}

		Key key = ks.getKey("desktop-nqov1ot", jksPassword.toCharArray());

		PrivateKey key1 = (PrivateKey) ks.getKey("desktop-nqov1ot", jksPassword.toCharArray());

		System.out.println(key);
		System.out.println(key1);

  

		String token = Jwts.builder().setHeaderParam("typ", "JWT").setIssuer("FMC Client ID")
				.setAudience("https://stageauth.conformx.com").setSubject("UserID:12345")
				.setIssuedAt(Date.from(currentTime)).setExpiration(Date.from(expiredTime))
				.setNotBefore(Date.from(currentTime)).setId(id).claim("auth_time", Date.from(authTime))
				.claim("auth_remoteip", "200.103.68.1").claim("idp", "https://FMC.com")
				.claim("acr", "https://auth.conformx.com/policy/acr/1")
				.claim("azp", "https://stage.solex.com/welcome/12345678-1234-1234-1234-123412341234")
				.signWith(SignatureAlgorithm.RS256, key).compact();
		return token;
	}

}
