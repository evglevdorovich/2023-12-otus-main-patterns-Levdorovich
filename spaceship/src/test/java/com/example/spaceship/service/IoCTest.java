package com.example.spaceship.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class IoCTest {
    private static final String DEPENDENCY_NAME = "dependency name";
    private static final Object[] ARGS = new Object[1];
    @Test
    void shouldResolveHandlers() {
        var ioCResolver = new IoC<>();
        var expectedHandlingResult = "test";

        var actualObjectAfterResolve = ioCResolver.resolve(DEPENDENCY_NAME, ARGS);

        assertThat(actualObjectAfterResolve).isEqualTo(expectedHandlingResult);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenHandlersNotFound() {
        var ioCResolver = new IoC<>();

        assertThatThrownBy(() -> ioCResolver.resolve(DEPENDENCY_NAME, ARGS)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFunctionNotFoundForValueClass() {
        var ioCResolver = new IoC<>();

        assertThatThrownBy(() -> ioCResolver.resolve(DEPENDENCY_NAME, ARGS)).isInstanceOf(IllegalArgumentException.class);
    }

}
