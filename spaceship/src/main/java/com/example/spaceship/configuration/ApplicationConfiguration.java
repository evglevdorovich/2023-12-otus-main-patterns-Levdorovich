package com.example.spaceship.configuration;

import com.example.spaceship.IoCAdapterInterceptor;
import com.example.spaceship.command.adapter.AdapterRegisterCreatorCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.core.Adapted;
import com.example.spaceship.service.adapter.AdapterCreator;
import com.example.spaceship.service.classes.ClassFinder;
import io.github.classgraph.ClassGraph;
import jakarta.annotation.PostConstruct;
import net.bytebuddy.ByteBuddy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ClassGraph classGraph() {
        return new ClassGraph()
                .enableAllInfo();
    }

    @Bean
    public ByteBuddy byteBuddy() {
        return new ByteBuddy();
    }

    @PostConstruct
    public void configure(ClassFinder classFinder, AdapterCreator adapterCreator) {
        new InitCommand().execute();
        new AdapterRegisterCreatorCommand().execute();
        var classes = classFinder.search("../", Adapted.class);
        adapterCreator.createAdapters(classes, IoCAdapterInterceptor.class, "Adapter");
    }
}
