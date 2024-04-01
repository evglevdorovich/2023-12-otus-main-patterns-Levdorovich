package com.example.spaceship.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.classgraph.ClassGraph;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import net.bytebuddy.ByteBuddy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

import java.security.interfaces.RSAPublicKey;
import java.time.Clock;

@Configuration
public class ApplicationDependencyConfiguration {
    @Bean
    public ClassGraph classGraph() {
        return new ClassGraph()
                .enableAllInfo();
    }

    @Bean
    public ByteBuddy byteBuddy() {
        return new ByteBuddy();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Algorithm algorithm(@Value("${jwt.public-key}") RSAPublicKey publicKey) {
        return Algorithm.RSA256(publicKey, null);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public static BeanFactoryPostProcessor conversionServicePostProcessor() {
        return new RsaKeyConversionServicePostProcessor();
    }
}
