package com.example.spaceship.core;

import com.example.spaceship.command.ChangeVelocityCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.InterpretCommand;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.scope.ClearCurrentScopeCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.model.MovableFinishable;
import com.example.spaceship.model.OperationRequest;
import com.example.spaceship.model.PlayerActionRequest;
import com.example.spaceship.model.Spaceship;
import com.example.spaceship.model.Vector;
import com.example.spaceship.model.VelocityAdjustable;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class IoCIT {
    @BeforeAll
    static void init() {
        new InitCommand().execute();
        var scope = IoC.resolve("IoC.Scope.Create", "test");
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
        InitCommand.setAlreadyExecuted(false);
        IoC.clear();
    }

    @AfterAll
    static void cleanUp() {
        InitCommand.setAlreadyExecuted(false);
        IoC.<ClearCurrentScopeCommand>resolve("IoC.Scope.Current.Clear").execute();
        IoC.clear();
    }

    @Test
    @SneakyThrows
    void shouldFinish() {
        registerFinish();

        var spaceShip = new Spaceship();
        var movableFinishable = IoC.<MovableFinishable>resolve("Adapter", MovableFinishable.class.getName(), spaceShip);

        assertThatNoException().isThrownBy(movableFinishable::finish);
    }

    @Test
    void shouldSetCorrectPosition() {
        registerGetMovablePosition();
        registerSetMovablePosition();
        var expectedPosition = new Vector(List.of(1, 2));
        var spaceShip = new Spaceship();

        var movableFinishableAdapter = IoC.<MovableFinishable>resolve("Adapter",
                MovableFinishable.class.getName(), spaceShip);
        movableFinishableAdapter.setPosition(expectedPosition);
        var actualPosition = movableFinishableAdapter.getPosition();

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }

    @Test
    void shouldGetCorrectVelocity() {
        registerGetMovableVelocity();
        var expectedVelocity = new Vector(List.of(2, 3));
        var spaceShip = new Spaceship();
        spaceShip.setVelocity(expectedVelocity);

        var movableFinishableAdapter = IoC.<MovableFinishable>resolve("Adapter",
                MovableFinishable.class.getName(), spaceShip);

        var actualVelocity = movableFinishableAdapter.getVelocity();

        assertThat(actualVelocity).isEqualTo(expectedVelocity);
    }

    @Test
    void shouldInterpretCommand() {
        var gameId = "gameId";
        var playerId = "playerId";
        var operationId = "changeVelocity";
        var expectedVelocity = List.of(2, 4);
        var arguments = new Object[]{new Vector(expectedVelocity)};
        var spaceShip = new Spaceship();
        spaceShip.setVelocity(new Vector(List.of(1, 2)));
        registerSetVelocity();
        var playerActionRequest = new PlayerActionRequest(gameId, playerId, new OperationRequest(operationId, arguments));

        registerGameObject(gameId, playerId, spaceShip);
        registerCommands(gameId, playerId, operationId);

        registerChangeVelocityCommand();

        var interpretCommand = new InterpretCommand(playerActionRequest);
        interpretCommand.execute();

        executeCommandFromQueue(playerActionRequest);

        var actualCoordinates = spaceShip.getVelocity().getCoordinates();

        assertThat(actualCoordinates).isEqualTo(expectedVelocity);
    }

    private static void executeCommandFromQueue(PlayerActionRequest playerActionRequest) {
        var queue = IoC.<Queue<Command>>resolve("Queue", playerActionRequest.getGameId());
        queue.poll().execute();
    }

    private static void registerChangeVelocityCommand() {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "changeVelocity", (Function<Object[], Object>)
                args -> {
                    var adjustable = IoC.<VelocityAdjustable>resolve("Adapter", VelocityAdjustable.class.getName(),
                            args[0]);
                    return new ChangeVelocityCommand(adjustable, (Vector) ((Object[]) args[1])[0]);
                }).execute();
    }

    private static void registerCommands(String gameId, String playerId, String operationId) {
        IoC.resolve("GameObject.Commands.Register", gameId, playerId, Set.of(operationId));
    }

    private static void registerGameObject(String gameId, String playerId, Spaceship spaceShip) {
        IoC.resolve("GameObject.Register", gameId, playerId, spaceShip);
    }

    private static void registerSetMovablePosition() {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "com.example.spaceship.model.Movable:setPosition",
                (Function<Object[], Object>) args -> {
                    try {
                        var method = args[0].getClass().getDeclaredMethod("setPosition", Vector.class);
                        return method.invoke(args[0], ((Object[]) args[1])[0]);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Cannot find method setPosition");
                    }
                }).execute();
    }

    private static void registerSetVelocity() {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "com.example.spaceship.model.VelocityAdjustable:setVelocity",
                (Function<Object[], Object>) args -> {
                    try {
                        var method = args[0].getClass().getDeclaredMethod("setVelocity", Vector.class);
                        return method.invoke(args[0], ((Object[]) args[1])[0]);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Cannot find method setVelocity");
                    }
                }).execute();
    }

    private static void registerGetMovablePosition() {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "com.example.spaceship.model.Movable:getPosition",
                (Function<Object[], Object>) args -> {
                    try {
                        var method = args[0].getClass().getDeclaredMethod("getPosition");
                        return method.invoke(args[0]);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Cannot find method getPosition");
                    }
                }).execute();
    }

    private static void registerGetMovableVelocity() {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "com.example.spaceship.model.Movable:getVelocity",
                (Function<Object[], Object>) args -> {
                    try {
                        var method = args[0].getClass().getDeclaredMethod("getVelocity");
                        return method.invoke(args[0]);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Cannot find method getVelocity");
                    }
                }).execute();
    }

    private static void registerFinish() {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register", "com.example.spaceship.model.MovableFinishable:finish",
                (Function<Object[], Object>) args -> {
                    try {
                        var method = args[0].getClass().getDeclaredMethod("finish");
                        return method.invoke(args[0]);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Cannot find method getPosition");
                    }
                }).execute();
    }

}
