package com.example.spaceship.factory;

import com.example.spaceship.command.AreaCollisionHandlerCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.Dimension;
import com.example.spaceship.model.Field;
import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Vector;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class CommandCollisionFactoryTest {

    private static final Queue<Command> COMMANDS = new ArrayDeque<>();

    private static final Field FIELD = new Field(new Dimension(List.of()), Map.of());

    private static final Movable MOVABLE = new MovableImpl();

    @Test
    void shouldCreateCollisionCommands() {
        var expectedCollisionCommand = AreaCollisionHandlerCommand.builder()
                .field(FIELD)
                .movable(MOVABLE)
                .commandQueue(COMMANDS)
                .build();
        var maxGameObjectDiameter = 4;
        var expectedOffsetCollisionCommand = List.of(expectedCollisionCommand.toBuilder()
                .offset(maxGameObjectDiameter / 2 + 1)
                .build());
        var expectedMacroCommand = new MacroCommand(List.of(expectedCollisionCommand, expectedOffsetCollisionCommand.get(0)));

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("Command.CollisionHandler.Regular", COMMANDS, FIELD, MOVABLE)).thenReturn(expectedCollisionCommand);
            ioC.when(() -> IoC.resolve("Command.CollisionHandler.Offset", expectedCollisionCommand, maxGameObjectDiameter))
                    .thenReturn(expectedOffsetCollisionCommand);
            var result = new CommandCollisionFactory().createCollisionCommand(COMMANDS, FIELD, MOVABLE, maxGameObjectDiameter);
            assertThat(result).isEqualTo(expectedMacroCommand);
        }
    }

    private record MovableImpl() implements Movable {

        @Override
        public Vector getPosition() {
            return null;
        }

        @Override
        public void setPosition(Vector position) {

        }

        @Override
        public Vector getVelocity() {
            return null;
        }
    }

}
