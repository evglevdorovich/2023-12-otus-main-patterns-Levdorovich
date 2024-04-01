package com.example.spaceship.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.spaceship.dto.GameUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessTokenServiceJwtTest {
    public static final int EXPIRE_IN_MINUTES = 10;
    private static final String ISSUER = "authorization";
    private static final String AUDIENCE = "spaceship-client";
    private static final String RSA = "RSA256";
    private static final byte[] SIGNATURE_BYTES = new byte[]{1, 2, 3, 4, 5, 1, 2, 3, 4};
    @Mock
    private Algorithm algorithm;
    @Mock
    private Clock clock;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private AccessTokenServiceJwt accessTokenServiceJwt;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(accessTokenServiceJwt, "issuer", ISSUER);
        ReflectionTestUtils.setField(accessTokenServiceJwt, "audience", AUDIENCE);
    }

    @Test
    void shouldVerifyToken() {
        var now = Instant.now();
        var header = getEncodedHeader();
        var payload = getEncodedPayload(now);

        var signature = Base64.getUrlEncoder().withoutPadding().encodeToString(SIGNATURE_BYTES);
        var tokenToVerify = String.format("%s.%s.%s", header, payload, signature);
        when(algorithm.getName()).thenReturn(RSA);

        var actualVerifiedToken = accessTokenServiceJwt.verify(tokenToVerify);
        assertThat(actualVerifiedToken.getToken()).isEqualTo(tokenToVerify);
    }

    @Test
    void shouldGetPayloadFromToken() {
        var token = getToken();
        var username = "John Doe";
        var gameId = "2";
        var expectedUserDto = new GameUser(username, gameId);

        var userDto = accessTokenServiceJwt.getPayload(token, GameUser.class);
        assertThat(userDto).isEqualTo(expectedUserDto);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenFailedToRead() {
        var incorrectToken = "incorrectToken";

        assertThatThrownBy(() -> accessTokenServiceJwt.getPayload(incorrectToken, GameUser.class)).isInstanceOf(RuntimeException.class);
    }


    private static String encode(String json) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    private static String getEncodedPayload(Instant now) {
        var payloadJson = """
                {"iss":"%s","aud":"%s","exp":%d,"username":"user1"}""".formatted(
                ISSUER, AUDIENCE, now.plus(EXPIRE_IN_MINUTES, ChronoUnit.MINUTES).getEpochSecond());
        return encode(payloadJson);
    }

    private static String getEncodedHeader() {
        var headerJson = """
                {"alg":"%s","typ":"JWT"}""".formatted(RSA);
        return encode(headerJson);
    }

    private static String getToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMiwiZ2FtZUlkIjoyfQ." +
                "azHeERT36CT91HdwFdZaIuXnExRk5gqYKDC3EW9qBcI";
    }
}
