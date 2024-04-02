package com.example.spaceship.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.RegisteredClaims;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceJwt implements AccessTokenService {
    private final Algorithm algorithm;
    private final ObjectMapper objectMapper;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.audience}")
    private String audience;

    @Override
    public DecodedJWT verify(String token) {
        return JWT.require(algorithm)
                .withAudience(audience)
                .withClaimPresence(RegisteredClaims.EXPIRES_AT)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    @Override
    public <T> T getPayload(String token, Class<T> clazz) {
        try {
            var payload = Base64.decodeBase64(JWT.decode(token).getPayload());
            return objectMapper.readValue(payload, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot parse payload", e);
        }
    }
}
