package com.example.spaceship.service;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface AccessTokenService {
    DecodedJWT verify(String token);
    <T> T getPayload(String token, Class<T> clazz);
}
