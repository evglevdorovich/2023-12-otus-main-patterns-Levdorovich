package com.example.spaceship.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenUtils {
    public String withoutBearer(String token) {
        var bearerPrefix = "Bearer ";
        return token.replaceFirst(bearerPrefix, "");
    }
}
