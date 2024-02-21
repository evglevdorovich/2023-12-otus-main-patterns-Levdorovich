package com.example.spaceship.service.classes;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ClassFinder {
    List<Class<?>> search(String packageName, Class<? extends Annotation> annotation);
}
