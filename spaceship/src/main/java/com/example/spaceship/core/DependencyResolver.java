package com.example.spaceship.core;

public interface DependencyResolver {
    Object resolve(String dependencyName, Object[] args);
}
