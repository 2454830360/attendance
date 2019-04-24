package com.system.attendance.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class Token {

    /**
     * 加密
     * @param userId
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     */
    private String createJWT(String userId, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("hello6341532");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(userId)
		.setIssuedAt(now)
		.setSubject(subject)
		.setIssuer(issuer)
		.signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
		long expMillis = nowMillis + ttlMillis;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * 解密
     * @param jwt
     */
    private void parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
		.setSigningKey(DatatypeConverter.parseBase64Binary("hello6341532"))
		.parseClaimsJws(jwt).getBody();
        System.out.println("userId: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }

    public static void main(String[] args) {

        Token t = new Token();
        //加密
        String jwt = t.createJWT("1001", "abcdefg", "hijklmn", 20190422);
        System.out.println(jwt);

        //解密
        t.parseJWT(jwt);
    }
}