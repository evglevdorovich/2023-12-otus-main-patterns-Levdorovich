package com.example.spaceship.command.adapter;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class AdapterRegisterCommandTest extends IoCSetUpTest {

    @Test
    void shouldRegisterAdapters() {
        Class<?>[] adapters = new Class[]{Integer.class, Long.class};

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {

            new AdapterRegisterCommand(adapters).execute();

            ioC.verify(() -> IoC.resolve("Adapter.Register", adapters));
        }
    }
}
