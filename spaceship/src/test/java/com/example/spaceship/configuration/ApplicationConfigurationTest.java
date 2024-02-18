package com.example.spaceship.configuration;

import com.example.spaceship.IoCAdapterInterceptor;
import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.core.Adapted;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;
import com.example.spaceship.service.adapter.AdapterCreator;
import com.example.spaceship.service.classes.ClassFinder;
import io.github.classgraph.ClassGraph;
import net.bytebuddy.ByteBuddy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigurationTest extends IoCSetUpTest {
    @Mock
    private ClassFinder classFinder;
    @Mock
    private AdapterCreator adapterCreator;

    @InjectMocks
    private ApplicationConfiguration appConfiguration;

    @Test
    void shouldCorrectlyInitialize() {
        appConfiguration.configure();

        assertThat(InitCommand.isAlreadyExecuted()).isTrue();
        var expectedCurrentScopeDependencyResolutionNames = new String[]{"IoC.Scope.Parent", "Adapter.Register"};
        var expectedRootScopeDependencyResolutionNames = new String[]{"IoC.Scope.Current.Set", "IoC.Scope.Current.Clear", "IoC.Scope.Current",
                "IoC.Scope.Create.Empty", "IoC.Scope.Create", "IoC.Register", "IoC.Scope.Parent"};

        assertThat(IoC.<Scope>resolve("IoC.Scope.Parent").dependencyResolutions())
                .containsKeys(expectedRootScopeDependencyResolutionNames);
        assertThat(IoC.<Scope>resolve("IoC.Scope.Current", "Adapter.Register").dependencyResolutions())
                .containsKeys(expectedCurrentScopeDependencyResolutionNames);
    }

    @Test
    void shouldCreateAdapters() {
        List<Class<?>> classesToFind = List.of(Integer.class, String.class);
        var classesMock = mock(List.class);

        when(classFinder.search("../", Adapted.class)).thenReturn(classesToFind);
        when(adapterCreator.createAdapters(classesToFind, IoCAdapterInterceptor.class, "Adapter")).thenReturn(classesMock);
        appConfiguration.configure();
    }
}
