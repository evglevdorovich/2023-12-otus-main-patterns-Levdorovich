package com.example.spaceship.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.classgraph.ClassGraph;
import io.swagger.v3.oas.models.OpenAPI;
import net.bytebuddy.ByteBuddy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

import java.security.interfaces.RSAPublicKey;
import java.time.Clock;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ApplicationDependencyConfigurationTest {
    @Test
    void shouldReturnByteBuddy() {
        ApplicationDependencyConfiguration appConfiguration = new ApplicationDependencyConfiguration();
        var expectedResult = new ByteBuddy();

        var actualResult = appConfiguration.byteBuddy();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnClassGraphWithAllInfoEnabled() {
        ApplicationDependencyConfiguration appConfiguration = new ApplicationDependencyConfiguration();

        var actualResult = appConfiguration.classGraph();

        assertThat(actualResult).isInstanceOf(ClassGraph.class);
    }

    @Test
    void shouldReturnClock() {
        ApplicationDependencyConfiguration appConfiguration = new ApplicationDependencyConfiguration();

        var expectedClock = Clock.systemDefaultZone();

        assertThat(appConfiguration.clock()).isEqualTo(expectedClock);
    }

    @Test
    void shouldReturnInitializedAlgorithm() {
        ApplicationDependencyConfiguration appConfiguration = new ApplicationDependencyConfiguration();
        var publicKey = Mockito.mock(RSAPublicKey.class);

        var actualAlgorithm = appConfiguration.algorithm(publicKey);
        assertThat(actualAlgorithm).isInstanceOf(Algorithm.class);
    }

    @Test
    void shouldReturnRsaPostProcessor() {
        var actualRegistrationBean = ApplicationDependencyConfiguration.conversionServicePostProcessor();

        assertThat(actualRegistrationBean).isInstanceOf(RsaKeyConversionServicePostProcessor.class);
    }

    @Test
    void shouldReturnOpeApi() {
        ApplicationDependencyConfiguration appConfiguration = new ApplicationDependencyConfiguration();

        var actualOpenApi = appConfiguration.openAPI();

        assertThat(actualOpenApi).isInstanceOf(OpenAPI.class);
    }
}
