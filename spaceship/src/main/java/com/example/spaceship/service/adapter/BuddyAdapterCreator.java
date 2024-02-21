package com.example.spaceship.service.adapter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuddyAdapterCreator implements AdapterCreator {
    private final ByteBuddy byteBuddy;

    @Override
    @SneakyThrows
    public List<? extends Class<?>> createAdapters(List<Class<?>> classesToAdapt, Class<?> adapterInterceptor, String adapterPostfix) {
        var objectConstructor = Object.class.getConstructor();
        return classesToAdapt.stream()
                .map(cl -> byteBuddy.subclass(Object.class)
                        .implement(cl)
                        .name(cl.getName() + adapterPostfix)
                        .defineField("obj", Object.class, Visibility.PRIVATE)
                        .defineConstructor(Visibility.PUBLIC)
                        .withParameters(Object.class)
                        .intercept(MethodCall.invoke(objectConstructor).andThen(FieldAccessor.ofField("obj").setsArgumentAt(0)))
                        .method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
                        .intercept(MethodDelegation.to(adapterInterceptor))
                        .make()
                        .load(cl.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                        .getLoaded()
                )
                .toList();
    }
}
