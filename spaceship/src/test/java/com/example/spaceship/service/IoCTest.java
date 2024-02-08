package com.example.spaceship.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IoCTest {
    @Test
    void shouldResolveHandlers() {
        var ioCResolver = new IoC<Object, Exception, String>();
        var keyObject = new Object();
        var valueObject = new Exception();
        var expectedHandlingResult = "test";
        ioCResolver.registerHandler(keyObject.getClass(), valueObject.getClass(),
                (k, v) -> expectedHandlingResult);

        var actualObjectAfterResolve = ioCResolver.resolve(keyObject, valueObject);

        assertThat(actualObjectAfterResolve).isEqualTo(expectedHandlingResult);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenHandlersNotFound() {
        var ioCResolver = new IoC<Object, Exception, String>();
        var keyObject = new Object();
        var valueObject = new Exception();

        assertThatThrownBy(() -> ioCResolver.resolve(keyObject, valueObject)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFunctionNotFoundForValueClass() {
        var ioCResolver = new IoC<Object, Exception, String>();
        var keyObject = new Object();
        var notRegisteredValueObject = new Exception();
        var registeredValueObject = new IllegalArgumentException();
        var expectedObjectAfterResolve = "exp";

        ioCResolver.registerHandler(keyObject.getClass(), registeredValueObject.getClass(),
                (k, v) -> expectedObjectAfterResolve);

        assertThatThrownBy(() -> ioCResolver.resolve(keyObject, notRegisteredValueObject)).isInstanceOf(IllegalArgumentException.class);
    }

}
