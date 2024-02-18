package com.example.spaceship.configuration;

import io.github.classgraph.ClassGraph;
import net.bytebuddy.ByteBuddy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationDependencyConfiguration {
    @Bean
    public ClassGraph classGraph() {
        return new ClassGraph()
                .enableAllInfo();
    }

    @Bean
    public ByteBuddy byteBuddy() {
        return new ByteBuddy();
    }
}
