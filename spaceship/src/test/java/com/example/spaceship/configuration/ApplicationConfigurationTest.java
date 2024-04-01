package com.example.spaceship.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.spaceship.IoCAdapterInterceptor;
import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.core.Adapted;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;
import com.example.spaceship.service.adapter.AdapterCreator;
import com.example.spaceship.service.classes.ClassFinder;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

import java.security.interfaces.RSAPublicKey;
import java.time.Clock;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
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
        var expectedCurrentScopeDependencyResolutionNames = new String[]{"IoC.Scope.Parent", "Adapter.Register", "GameObject",
                "GameObject.Commands.Register", "GameObject.Commands.Validate", "Queue.Register", "Queue"};
        var expectedRootScopeDependencyResolutionNames = new String[]{"IoC.Scope.Current.Set", "IoC.Scope.Current.Clear", "IoC.Scope.Current",
                "IoC.Scope.Create.Empty", "IoC.Scope.Create", "IoC.Register", "IoC.Scope.Parent"};

        assertThat(IoC.<Scope>resolve("IoC.Scope.Parent").dependencyResolutions())
                .containsKeys(expectedRootScopeDependencyResolutionNames);
        assertThat(IoC.<Scope>resolve("IoC.Scope.Current", "Adapter.Register").dependencyResolutions())
                .containsKeys(expectedCurrentScopeDependencyResolutionNames);
    }

    @Test
    void shouldExecuteRegistrations() {
        List<Class<?>> classesToFind = List.of(Integer.class, String.class);
        var classesMock = mock(List.class);
        var classes = new Object[]{};
        var registerDependencyCommand = mock(RegisterDependencyCommand.class);

        when(classFinder.search("../", Adapted.class)).thenReturn(classesToFind);
        when(adapterCreator.createAdapters(classesToFind, IoCAdapterInterceptor.class, "Adapter")).thenReturn(classesMock);
        when(classesMock.toArray()).thenReturn(classes);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.<RegisterDependencyCommand>resolve(eq("IoC.Register"), anyString(), any(Function.class)))
                    .thenReturn(registerDependencyCommand);
            appConfiguration.configure();
        }
        verify(registerDependencyCommand, times(8)).execute();
    }

    @Test
    void shouldReturnClock() {
        var expectedClock = Clock.systemDefaultZone();

        assertThat(appConfiguration.clock()).isEqualTo(expectedClock);
    }

    @Test
    void shouldReturnInitializedAlgorithm() {
        var publicKey = Mockito.mock(RSAPublicKey.class);

        var actualAlgorithm = appConfiguration.algorithm(publicKey);
        assertThat(actualAlgorithm).isInstanceOf(Algorithm.class);
    }

    @Test
    void shouldReturnRsaPostProcessor() {
        var actualRegistrationBean = ApplicationConfiguration.conversionServicePostProcessor();
        assertThat(actualRegistrationBean).isInstanceOf(RsaKeyConversionServicePostProcessor.class);
    }

    @Test
    void shouldReturnOpeApi() {
        var actualOpenApi = appConfiguration.openAPI();
        assertThat(actualOpenApi).isInstanceOf(OpenAPI.class);
    }

}
