package com.example.spaceship.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class IoCResolver<T, U, R> {
    private final Map<Class<? extends U>, Boolean> valuePlugins;

    private final Map<Class<? extends T>, Map<Class<? extends U>, BiFunction<T, U, R>>> resolvableHandlers;

    public IoCResolver() {
        this.valuePlugins = new ConcurrentHashMap<>();
        this.resolvableHandlers = new ConcurrentHashMap<>();
    }

    public R resolve(T keyObject, U valueObject) {
        var handlers = resolvableHandlers.get(keyObject.getClass());

        if (handlers == null) {
            throw new IllegalArgumentException("functions are not registered for a key class: " + keyObject.getClass().getName());
        }

        var handler = handlers.get(valueObject.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("cannot find handler for a key class" + keyObject.getClass().getName() + "with value class: " +
                    valueObject.getClass().getName());
        }

        return handler.apply(keyObject, valueObject);
    }

    public void registerHandler(Class<? extends T> keyClass, Class<? extends U> valueClass, BiFunction<T, U, R> handler) {
        var functions = resolvableHandlers.computeIfAbsent(keyClass, k -> new ConcurrentHashMap<>());
        functions.put(valueClass, handler);
        resolvableHandlers.put(keyClass, functions);
    }

    public void registerValuePlugin(Class<? extends U> valueClass, boolean isEnabled) {
        valuePlugins.put(valueClass, isEnabled);
    }
}
