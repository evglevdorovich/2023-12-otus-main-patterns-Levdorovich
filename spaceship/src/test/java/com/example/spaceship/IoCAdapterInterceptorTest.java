package com.example.spaceship;

import com.example.spaceship.core.IoC;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class IoCAdapterInterceptorTest {
    @Test
    @SneakyThrows
    void shouldCallIoCResolveForTheMethodImplementation() {
        var objectMethod = Object.class.getDeclaredMethod("toString");
        var expectedDependencyName = Object.class.getName() + ":" + objectMethod.getName();
        var args = new Object[]{};
        var instance = new ClassWithObject();

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            IoCAdapterInterceptor.intercept(objectMethod, args, instance);
            ioC.verify(() -> IoC.resolve(expectedDependencyName, instance.obj, args));
        }
    }

    private static class ClassWithObject {
        private Object obj;
    }

}
