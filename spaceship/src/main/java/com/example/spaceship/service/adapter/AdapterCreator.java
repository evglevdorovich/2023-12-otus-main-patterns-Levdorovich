package com.example.spaceship.service.adapter;

import java.util.List;

public interface AdapterCreator {
    List<? extends Class<?>> createAdapters(List<Class<?>> classesToAdapt, Class<?> adapterInterceptor, String adapterPostfix);
}
