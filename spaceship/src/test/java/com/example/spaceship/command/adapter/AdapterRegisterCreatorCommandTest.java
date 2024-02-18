package com.example.spaceship.command.adapter;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class AdapterRegisterCreatorCommandTest extends IoCSetUpTest {

    @Test
    void shouldRegisterAdaptersCreator() {
        var registerCommand = mock(RegisterDependencyCommand.class);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve(eq("IoC.Register"), eq("Adapter.Register"), any(Function.class))).thenReturn(registerCommand);
            new AdapterRegisterCreatorCommand().execute();
        }

        verify(registerCommand).execute();
    }

}
