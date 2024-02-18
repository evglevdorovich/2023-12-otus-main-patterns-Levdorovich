package com.example.spaceship.configuration;

import com.example.spaceship.IoCAdapterInterceptor;
import com.example.spaceship.command.adapter.AdapterRegisterCommand;
import com.example.spaceship.command.adapter.AdapterRegisterCreatorCommand;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.core.Adapted;
import com.example.spaceship.core.AdapterResolver;
import com.example.spaceship.core.IoC;
import com.example.spaceship.core.SimpleAdapterResolver;
import com.example.spaceship.service.adapter.AdapterCreator;
import com.example.spaceship.service.classes.ClassFinder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final ClassFinder classFinder;
    private final AdapterCreator adapterCreator;
    private final AdapterResolver adapterResolver;

    @PostConstruct
    public void configure() {
        new InitCommand().execute();
        new AdapterRegisterCreatorCommand().execute();
        var classes = classFinder.search("../", Adapted.class);
        var adapters = adapterCreator.createAdapters(classes, IoCAdapterInterceptor.class, "Adapter").toArray();
        new AdapterRegisterCommand(adapters).execute();
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "Adapter",
                (Function<Object[], Object>) (args) -> adapterResolver.resolve((String) args[0], args[1])).execute();
    }
}
