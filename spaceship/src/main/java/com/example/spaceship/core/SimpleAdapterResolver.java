package com.example.spaceship.core;

import org.springframework.stereotype.Component;

@Component
public class SimpleAdapterResolver implements AdapterResolver{
    @Override
    public Object resolve(String interfaceName, Object objToAdapt) {
        return IoC.resolve("Adapter." + interfaceName + "Adapter", objToAdapt);
    }
}
