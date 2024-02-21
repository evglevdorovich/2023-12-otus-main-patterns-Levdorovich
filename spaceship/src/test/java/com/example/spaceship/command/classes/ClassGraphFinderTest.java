package com.example.spaceship.command.classes;

import com.example.spaceship.service.classes.ClassGraphFinder;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassGraphFinderTest {
    @Mock
    private ClassGraph classGraph;
    @Mock
    private ScanResult scanResult;
    @Mock
    private ClassInfoList classInfoList;
    @InjectMocks
    private ClassGraphFinder classGraphFinder;

    @Test
    void shouldLoadAllClasses() {
        String packageName = "./";
        var annotationToFound = ExtendWith.class;
        var expectedClassesToFound = List.of(Object.class, List.class);
        when(classGraph.acceptPackages(packageName)).thenReturn(classGraph);
        when(classGraph.scan()).thenReturn(scanResult);
        when(scanResult.getClassesWithAnnotation(annotationToFound)).thenReturn(classInfoList);
        when(classInfoList.loadClasses()).thenReturn(expectedClassesToFound);

        var foundClasses = classGraphFinder.search(packageName, annotationToFound);

        assertThat(foundClasses).isEqualTo(expectedClassesToFound);
    }
}
