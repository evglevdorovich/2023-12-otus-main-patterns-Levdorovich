package com.example.spaceship.service.adapter;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuddyAdapterCreatorTest {
    @Mock
    private ByteBuddy byteBuddy;
    @InjectMocks
    private BuddyAdapterCreator buddyAdapterCreator;

    @Test
    @SneakyThrows
    void shouldCreateAdapters() {
        var adaptedClasses = List.of(Object.class, String.class);
        var adapterInterceptor = Double.class;
        var adapterPostfix = "Adapter";
        var expectedCreatedAdapters = List.of(Integer.class, Long.class);
        var objectConstructor = Object.class.getConstructor();

        mockBuddyMethods(adaptedClasses, adapterPostfix, objectConstructor, adapterInterceptor);

        var actualCreatedAdapters = buddyAdapterCreator.createAdapters(adaptedClasses, adapterInterceptor, adapterPostfix);

        assertThat(actualCreatedAdapters).isEqualTo(expectedCreatedAdapters);
    }

    @SuppressWarnings("unchecked")
    private void mockBuddyMethods(List<Class<?>> adaptedClasses, String adapterPostfix, Constructor<Object> objectConstructor,
                                  Class<Double> adapterInterceptor) {
        var builderMock = mock(DynamicType.Builder.class);
        var implementationDefinitionMock = mock(DynamicType.Builder.MethodDefinition.ImplementationDefinition.Optional.class);
        var valuableObjectMock = mock(DynamicType.Builder.FieldDefinition.Optional.Valuable.class);
        var methodDefinitionMock = mock(DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial.class);
        var exceptionDefinitionMock = mock(DynamicType.Builder.MethodDefinition.ExceptionDefinition.class);
        var receiverTypeDefinitionMock = mock(DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition.class);
        var methodImplementationDefinitionMock = mock(DynamicType.Builder.MethodDefinition.ImplementationDefinition.class);
        var unloaded = mock(DynamicType.Unloaded.class);
        var loaded = mock(DynamicType.Loaded.class);

        when(byteBuddy.subclass(Object.class)).thenReturn(builderMock);

        adaptedClasses.forEach(cl -> {
            when(implementationDefinitionMock.name(cl.getName() + adapterPostfix)).thenReturn(builderMock);
            when(builderMock.implement(cl)).thenReturn(implementationDefinitionMock);
            when(unloaded.load(cl.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)).thenReturn(loaded);
            when(loaded.getLoaded()).thenReturn(Integer.class).thenReturn(Long.class);
        });

        when(builderMock.defineField("obj", Object.class, Visibility.PRIVATE)).thenReturn(valuableObjectMock);
        when(valuableObjectMock.defineConstructor(Visibility.PUBLIC)).thenReturn(methodDefinitionMock);
        when(methodDefinitionMock.withParameters(Object.class)).thenReturn(exceptionDefinitionMock);
        when(exceptionDefinitionMock.intercept((MethodCall.invoke(objectConstructor).andThen(FieldAccessor.ofField("obj").setsArgumentAt(0)))))
                .thenReturn(receiverTypeDefinitionMock);
        when(receiverTypeDefinitionMock.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class))))
                .thenReturn(methodImplementationDefinitionMock);
        when(methodImplementationDefinitionMock.intercept(MethodDelegation.to(adapterInterceptor))).thenReturn(receiverTypeDefinitionMock);
        when(receiverTypeDefinitionMock.make()).thenReturn(unloaded);
    }

}
