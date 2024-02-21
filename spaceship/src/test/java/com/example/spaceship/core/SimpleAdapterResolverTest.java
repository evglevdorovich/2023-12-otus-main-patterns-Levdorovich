package com.example.spaceship.core;

import com.example.spaceship.IoCSetUpTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class SimpleAdapterResolverTest extends IoCSetUpTest {

    @Test
    void shouldResolveObject() {
        var simpleAdapterResolver = new SimpleAdapterResolver();
        var interfaceName = String.class.getName();
        var object = new Object();
        var adapteredObject = new Object();

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> {
                IoC.resolve("Adapter." + interfaceName + "Adapter", object);
            }).thenReturn(adapteredObject);
            var actualResolvedAdapteredObject = simpleAdapterResolver.resolve(interfaceName, object);
            assertThat(actualResolvedAdapteredObject).isEqualTo(adapteredObject);
        }
    }
}
