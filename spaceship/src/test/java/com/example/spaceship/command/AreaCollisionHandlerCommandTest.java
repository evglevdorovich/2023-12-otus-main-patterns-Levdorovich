package com.example.spaceship.command;

import com.example.spaceship.core.IoC;
import com.example.spaceship.model.Area;
import com.example.spaceship.model.Collidable;
import com.example.spaceship.model.Dimension;
import com.example.spaceship.model.Field;
import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Vector;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AreaCollisionHandlerCommandTest {
    private static final Dimension DIMENSION = new Dimension(List.of(3L, 4L, 5L));
    private static final Vector VELOCITY = new Vector(List.of(0, 0, -1));
    @Mock
    private Queue<Command> commands;
    @Mock
    private Collidable collidable;
    @Mock
    private Collidable secondCollidable;

    @Test
    void shouldCreateCollisionCommandsWithChangingArea() {
        var expectedIndex = "1:1:2:";
        var indexAfterMove = "1:1:1:";
        var position = new Vector(List.of(3, 5, 10));
        var positionAfterMove = new Vector(List.of(3, 5, 9));
        var movable = new MovableImpl(positionAfterMove, VELOCITY);
        var otherMovable = new MovableImpl(position, VELOCITY);
        var area = new Area(new HashSet<>(Set.of(movable)));
        var areaWithOtherMovable = new Area(new HashSet<>(Set.of(otherMovable)));

        var field = new Field(DIMENSION, Map.of(expectedIndex, area, indexAfterMove, areaWithOtherMovable));

        var areaCollisionHandlerCommand = buildAreaCollisionHandlerCommand(movable, position, field);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("Adapter", Collidable.class.getName(), movable)).thenReturn(collidable);
            ioC.when(() -> IoC.resolve("Adapter", Collidable.class.getName(), otherMovable)).thenReturn(secondCollidable);
            areaCollisionHandlerCommand.execute();
        }

        verify(commands).addAll(List.of(new CheckCollisionCommand(collidable, secondCollidable)));
        assertThat(area.movables()).isEmpty();
        assertThat(areaWithOtherMovable.movables()).containsExactlyInAnyOrder(movable, otherMovable);
    }

    @Test
    void shouldCreateCollisionCommandsWithoutChangingArea() {
        var indexExpected = "1:1:1:";
        var position = new Vector(List.of(3, 5, 8));
        var positionAfterMove = new Vector(List.of(3, 5, 9));
        var movable = new MovableImpl(positionAfterMove, VELOCITY);
        var otherMovable = new MovableImpl(position, VELOCITY);
        var area = new Area(new HashSet<>(Set.of(movable, otherMovable)));
        var field = new Field(DIMENSION, Map.of(indexExpected, area));

        var areaCollisionHandlerCommand = buildAreaCollisionHandlerCommand(movable, position, field);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("Adapter", Collidable.class.getName(), movable)).thenReturn(collidable);
            ioC.when(() -> IoC.resolve("Adapter", Collidable.class.getName(), otherMovable)).thenReturn(secondCollidable);
            areaCollisionHandlerCommand.execute();
        }

        verify(commands).addAll(List.of(new CheckCollisionCommand(collidable, secondCollidable)));
        assertThat(area.movables()).containsExactlyInAnyOrder(movable, otherMovable);
    }

    private AreaCollisionHandlerCommand buildAreaCollisionHandlerCommand(MovableImpl movable, Vector position, Field field) {
        return AreaCollisionHandlerCommand.builder()
                .commandQueue(commands)
                .movable(movable)
                .previousPosition(position)
                .field(field)
                .build();
    }

    @Data
    @AllArgsConstructor
    private static class MovableImpl implements Movable {
        private Vector position;
        @Setter(value = AccessLevel.NONE)
        private Vector velocity;
    }

}
