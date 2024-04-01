package com.example.spaceship.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenUtilsTest {
    @Test
    void shouldCutBearer() {
        var bearerToken = "Bearer token";
        var token = "token";

        var actualResult = TokenUtils.withoutBearer(bearerToken);

        assertThat(actualResult).isEqualTo(token);
    }
}
