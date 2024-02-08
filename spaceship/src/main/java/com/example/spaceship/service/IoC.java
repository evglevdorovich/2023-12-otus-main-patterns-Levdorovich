package com.example.spaceship.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@Component
public class IoC<T> {

    public T resolve(String dependency, Object[] args) {
//        var handlers = resolvableHandlers.get(keyObject.getClass());
//
//        if (handlers == null) {
//            throw new IllegalArgumentException("functions are not registered for a key class: " + keyObject.getClass().getName());
//        }
//
//        var handler = handlers.get(valueObject.getClass());
//        if (handler == null) {
//            throw new IllegalArgumentException("cannot find handler for a key class" + keyObject.getClass().getName() + "with value class: " +
//                    valueObject.getClass().getName());
//        }

//        return handler.apply(keyObject, valueObject);
        return null;
    }
}
