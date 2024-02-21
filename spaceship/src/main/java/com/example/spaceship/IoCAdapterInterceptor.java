package com.example.spaceship;

import com.example.spaceship.core.IoC;
import lombok.SneakyThrows;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class IoCAdapterInterceptor {
    @RuntimeType
    @SneakyThrows
    public static Object intercept(@Origin Method method, @AllArguments @RuntimeType Object[] args, @This Object instance) {
        var objField = instance.getClass().getDeclaredField("obj");
        objField.setAccessible(true);
        String dependencyName = method.getDeclaringClass().getName() + ":" + method.getName();

        return IoC.resolve(dependencyName, objField.get(instance), args);
    }
}
