package com.example.spaceship.configuration;

import io.github.classgraph.ClassGraph;
import net.bytebuddy.ByteBuddy;
import org.junit.jupiter.api.Test;

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
}
