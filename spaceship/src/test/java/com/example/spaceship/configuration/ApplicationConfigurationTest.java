package com.example.spaceship.configuration;

import io.github.classgraph.ClassGraph;
import net.bytebuddy.ByteBuddy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationConfigurationTest {
    @Test
    void shouldReturnByteBuddy() {
        ApplicationConfiguration appConfiguration = new ApplicationConfiguration();
        var expectedResult = new ByteBuddy();

        var actualResult = appConfiguration.byteBuddy();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnClassGraphWithAllInfoEnabled() {
        ApplicationConfiguration appConfiguration = new ApplicationConfiguration();

        var actualResult = appConfiguration.classGraph();

        assertThat(actualResult).isInstanceOf(ClassGraph.class);
    }
}
