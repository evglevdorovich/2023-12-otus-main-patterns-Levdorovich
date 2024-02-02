package com.example.spaceship.command;

import com.example.spaceship.exception.CommandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MacroCommandTest {
    @Mock
    private Command firstCommand;

    @Mock
    private Command secondCommand;

    @Test
    void shouldExecuteAllCommands() {
        var macroCommand = new MacroCommand(List.of(firstCommand, secondCommand));

        macroCommand.execute();

        verify(firstCommand).execute();
        verify(secondCommand).execute();
    }

    @Test
    void shouldThrowMacroCommandExceptionWhenExceptionIsThrown() {
        var macroCommand = new MacroCommand(List.of(firstCommand, secondCommand));
        doThrow(RuntimeException.class).when(firstCommand).execute();

        assertThatThrownBy(macroCommand::execute).isInstanceOf(CommandException.class);
    }

}
