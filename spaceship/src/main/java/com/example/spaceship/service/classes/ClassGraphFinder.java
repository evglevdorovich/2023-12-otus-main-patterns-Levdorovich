package com.example.spaceship.service.classes;

import com.example.spaceship.service.classes.ClassFinder;
import io.github.classgraph.ClassGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassGraphFinder implements ClassFinder {
    private final ClassGraph classGraph;

    public List<Class<?>> search(String packageName, Class<? extends Annotation> annotation) {
        try (var scanResult = classGraph.acceptPackages(packageName).scan()) {
            return scanResult.getClassesWithAnnotation(annotation).loadClasses();
        }
    }
}
