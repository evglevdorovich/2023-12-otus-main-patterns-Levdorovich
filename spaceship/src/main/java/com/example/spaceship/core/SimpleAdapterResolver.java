package com.example.spaceship.core;

public class SimpleAdapterResolver implements AdapterResolver{
    @Override
    public Object resolve(String interfaceName, Object objToAdapt) {
        return IoC.resolve("Adapter." + interfaceName + "Adapter", objToAdapt);
    }
}
